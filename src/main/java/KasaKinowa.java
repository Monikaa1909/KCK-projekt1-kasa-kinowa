import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import models.Category;
import models.Movie;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import models.MovieDate;

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


    public static void selectMovie(Category c) {
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.setLayoutData(BorderLayout.Location.CENTER);

        Label label = new Label("Wybierz film, która Cię interesuje:");

        ActionListBox actionListBox = new ActionListBox();
        List<Movie> movies = db.getMovies(c.getName());
        for (Movie m : movies) {
            actionListBox.addItem(m.getTitle(), new Runnable() {
                @Override
                public void run() {
                    try {
                        selectMovieDay(m);
                    } catch (IOException | SQLException | ParseException e) {
                        e.printStackTrace();
                    }
                    // zapis danych biletu
                }
            });
        }


        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(label);
        panel.addComponent(new EmptySpace());
        panel.addComponent(actionListBox);
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 2.")));
    }

    public static void selectCategory() {
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.setLayoutData(BorderLayout.Location.CENTER);

        Label label = new Label("Wybierz kategorię, która Cię interesuje:");

        ActionListBox actionListBox = new ActionListBox();
        List<Category> categories = db.getCategories();
        for (Category c : categories) {
            actionListBox.addItem(c.getName(), new Runnable() {
                @Override
                public void run() {
//                    if (c.getName().equals("Horror")) {
//                        selectMovie("Horror");
//                    }
//                    else if (c.getName().equals("Komedia")) {
//                        selectMovie("Komedia");
//                    }
//                    else if (c.getName().equals("Sci-Fi")) {
//                        selectMovie("Sci-Fi");
//                    }
//                    else if (c.getName().equals("Film romantyczny")) {
//                        selectMovie("Film romantyczny");
//                    }
                    selectMovie(c);
                    // zapis danych biletu
                }
            });
        }


        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(label);
        panel.addComponent(new EmptySpace());
        panel.addComponent(actionListBox);
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 1.")));
    }

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

    public static void start() throws IOException {
        window = new BasicWindow();
        window.setFixedSize(terminal.getTerminalSize());
        window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN));
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        panel.setLayoutData(BorderLayout.Location.CENTER);

        Label label = new Label("Kliknij ENTER aby rozpocząć zakup biletu");
        Button enterButton = new Button("Enter", new Runnable() {
            @Override
            public void run() {
                selectCategory();
            }
        });
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(label);
        panel.addComponent(new EmptySpace());
        panel.addComponent(enterButton);
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        window.setTitle("Samoobsługowa kasa kinowa");
        window.setComponent(panel.withBorder(Borders.doubleLine()));

        gui.addWindowAndWait(window);
    }

    public static void main(String[] args) throws IOException {
        terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK_BRIGHT));
        screen.startScreen();
        start();

    }
}

