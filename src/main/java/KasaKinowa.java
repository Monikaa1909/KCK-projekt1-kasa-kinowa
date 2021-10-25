import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import models.Category;
import models.Movie;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import models.MovieDate;
import models.Ticket;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class KasaKinowa {
    static Terminal terminal;
    static Screen screen;
    static MultiWindowTextGUI gui;
    static BasicWindow window;

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");

    static Database db = new Database();


    public static void selectMovieDay(Movie m) throws IOException, SQLException, ParseException {
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.setLayoutData(BorderLayout.Location.CENTER);

        Label label = new Label("Wybierz dzień, który Cię interesuje:");



        ActionListBox actionListBox = new ActionListBox();
        List<String> dates = db.getMovieDates(m);
        for (String d : dates) {
            actionListBox.addItem(d, new Runnable() {
                @Override
                public void run() {
                    try {
                        selectMovieHour(m, d);
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
//                    Date date10 = new SimpleDateFormat("YYYY-MM-dd").parse(dateString);

                    // zapis danych biletu
                }
            });
        }


        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(label);
        panel.addComponent(new EmptySpace());
        panel.addComponent(actionListBox);
        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 3.")));
    }

    public static void selectMovieHour(Movie m, String date) throws IOException, SQLException, ParseException {
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.setLayoutData(BorderLayout.Location.CENTER);

        Label label = new Label("Wybierz godzinę, która Cię interesuje:");

        ActionListBox actionListBox = new ActionListBox();
        List<String> hours = db.getMovieHours(m, date);

        for (String h : hours) {
            actionListBox.addItem(h, new Runnable() {
                @Override
                public void run() {
//                     select...();
//

                    // zapis danych biletu
                }
            });
        }


        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(label);
        panel.addComponent(new EmptySpace());
        panel.addComponent(actionListBox);
        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 4.")));
    }

    public static void selectMovie(Ticket ticket) {
        Panel panel = new Panel(new GridLayout(4));
        GridLayout gridLayout = (GridLayout)panel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        Label title = new Label("Wybierz film, który Cię interesuje:");
        title.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        ActionListBox actionListBox = new ActionListBox();
        actionListBox.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        List<Movie> movies = db.getMovies(ticket.getCategory().getName());
        for (Movie m : movies) {
            actionListBox.addItem(m.getTitle(), new Runnable() {
                @Override
                public void run() {
                    try {
                        ticket.setMovie(m);
                        selectMovieDay(m);
                    } catch (IOException | SQLException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Button backButton = new Button("Cofnij", new Runnable() {
            @Override
            public void run() {
                ticket.setCategory(null);
                selectCategory(ticket);
            }
        });
        backButton.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                1,
                1));

        Button cancelButton = new Button("Anuluj zakup biletu", new Runnable() {
            @Override
            public void run() {
                try {
                    start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        cancelButton.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                1,
                1));

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));

        panel.addComponent(new EmptySpace());
        panel.addComponent(title);
        panel.addComponent(new EmptySpace());

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));

        panel.addComponent(new EmptySpace());
        panel.addComponent(actionListBox);
        panel.addComponent(new EmptySpace());

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));
        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));
        panel.addComponent(new EmptySpace());
        panel.addComponent(backButton);
        panel.addComponent(cancelButton);
        panel.addComponent(new EmptySpace());


        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 2.")));
    }


    public static void selectCategory(Ticket ticket) {
        Panel panel = new Panel(new GridLayout(4));
        GridLayout gridLayout = (GridLayout)panel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        Label title = new Label("Wybierz kategorię, która Cię interesuje:");
        title.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        ActionListBox actionListBox = new ActionListBox();
        actionListBox.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        List<Category> categories = db.getCategories();
        for (Category c : categories) {
            actionListBox.addItem(c.getName(), new Runnable() {
                @Override
                public void run() {
                    ticket.setCategory(c);
                    selectMovie(ticket);
                }
            });
        }

        Button backButton = new Button("Cofnij", new Runnable() {
            @Override
            public void run() {
                try {
                    start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        backButton.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                1,
                1));

        Button cancelButton = new Button("Anuluj zakup biletu", new Runnable() {
            @Override
            public void run() {
                try {
                    start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        cancelButton.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                1,
                1));

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));

        panel.addComponent(new EmptySpace());
        panel.addComponent(title);
        panel.addComponent(new EmptySpace());

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));

        panel.addComponent(new EmptySpace());
        panel.addComponent(actionListBox);
        panel.addComponent(new EmptySpace());

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));
        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));
        panel.addComponent(new EmptySpace());
        panel.addComponent(backButton);
        panel.addComponent(cancelButton);
        panel.addComponent(new EmptySpace());

        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 1.")));
    }

    public static void start() throws IOException {
        Panel panel = new Panel(new GridLayout(4));
        GridLayout gridLayout = (GridLayout)panel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        Label title = new Label("Kliknij ENTER aby rozpocząć zakup biletu");
        title.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        Button enterButton = new Button("Enter", new Runnable() {
            @Override
            public void run() {
                Ticket ticket = new Ticket();
                selectCategory(ticket);
            }
        });
        enterButton.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));

        panel.addComponent(new EmptySpace());
        panel.addComponent(title);
        panel.addComponent(new EmptySpace());

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));

        panel.addComponent(new EmptySpace());
        panel.addComponent(enterButton);
        panel.addComponent(new EmptySpace());

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));

        window.setTitle("Samoobsługowa kasa kinowa");
        window.setComponent(panel.withBorder(Borders.doubleLine()));
        gui.addWindowAndWait(window);
    }

    public static void main(String[] args) throws IOException {
        terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK_BRIGHT));
        screen.startScreen();
        window = new BasicWindow();
        window.setFixedSize(terminal.getTerminalSize());
        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));

        start();
//        pomocnerzeczy();

    }



    public static void pomocnerzeczy() throws IOException {
        window = new BasicWindow();
        window.setFixedSize(terminal.getTerminalSize());
        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));
        Panel contentPanel = new Panel(new GridLayout(3));
        GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);
        Label title = new Label("This is a label that spans two columns");
        title.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.BEGINNING, // Horizontal alignment in the grid cell if the cell is larger than the component's preferred size
                GridLayout.Alignment.BEGINNING, // Vertical alignment in the grid cell if the cell is larger than the component's preferred size
                true,       // Give the component extra horizontal space if available
                false,        // Give the component extra vertical space if available
                3,                  // Horizontal span
                1));                  // Vertical span
        contentPanel.addComponent(title);
        contentPanel.addComponent(new EmptySpace());
        contentPanel.addComponent(new Label("Text Box (aligned)"));
        contentPanel.addComponent(new EmptySpace());
        contentPanel.addComponent(new EmptySpace());
        contentPanel.addComponent(
                new TextBox()
                        .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));
        contentPanel.addComponent(new Label("Password Box (right aligned)"));
        contentPanel.addComponent(
                new TextBox()
                        .setMask('*')
                        .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
        contentPanel.addComponent(new Label("Read-only Combo Box (forced size)"));
        List<String> timezonesAsStrings = new ArrayList<String>();
        for(String id: TimeZone.getAvailableIDs()) {
            timezonesAsStrings.add(id);
        }
        ComboBox<String> readOnlyComboBox = new ComboBox<String>(timezonesAsStrings);
        readOnlyComboBox.setReadOnly(true);
        readOnlyComboBox.setPreferredSize(new TerminalSize(20, 1));
        contentPanel.addComponent(readOnlyComboBox);

        contentPanel.addComponent(new Label("Editable Combo Box (filled)"));
        contentPanel.addComponent(
                new ComboBox<String>("Item #1", "Item #2", "Item #3", "Item #4")
                        .setReadOnly(false)
                        .setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1)));
        contentPanel.addComponent(new Label("Button (centered)"));
        contentPanel.addComponent(new Button("Button", new Runnable() {
            @Override
            public void run() {
                MessageDialog.showMessageDialog(gui, "MessageBox", "This is a message box", MessageDialogButton.OK);
            }
        }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)));
        contentPanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        contentPanel.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        contentPanel.addComponent(
                new Button("Close", new Runnable() {
                    @Override
                    public void run() {
                        window.close();
                    }
                }).setLayoutData(
                        GridLayout.createHorizontallyEndAlignedLayoutData(2)));
        window.setComponent(contentPanel);
        gui.addWindowAndWait(window);
    }
}



