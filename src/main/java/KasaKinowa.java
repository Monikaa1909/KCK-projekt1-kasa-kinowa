import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import models.Category;
import models.Movie;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import models.Seat;
import models.Ticket;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.color.ColorSpace;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class KasaKinowa {
    static Terminal terminal;
    static Screen screen;
    static MultiWindowTextGUI gui;
    static BasicWindow window;

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");

    static Database db = new Database();

    public static void selectTicket(Ticket ticket, Category c, Movie m, String date, String hour, Seat s){
        Panel panel = new Panel(new GridLayout(4));
        GridLayout gridLayout = (GridLayout)panel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        Label title = new Label("Sprawdź, czy dane na Twoim bilecie się zgadzają:");
        title.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        System.out.println(ticket.getMovie().getTitle() + " " + ticket.getDate() + " " + ticket.getHour() + " " +
                "" + ticket.getSeat() + " " + ticket.getType() + " " + ticket.isDiscount());

        Button discountYes = new Button("Tak", new Runnable() {
            @Override
            public void run() {
                String input = TextInputDialog.showDialog(gui, "Kod rabatowy", "" +
                        "   Wprowadź tu swój kod rabatowy:  " +
                        "", "");
                if (!input.equals("123")) {
                    MessageDialog.showMessageDialog(gui, "Wiadomość", "\n   Twój kod rabatowy jest niepoprawny :(    \n", MessageDialogButton.OK);
                }
                else {
//                    selectTicket(){}
                }
            }
        });

        discountYes.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        Button discountNo = new Button("No", new Runnable() {
            @Override
            public void run() {

            }
        });
        discountNo.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        Button backButton = new Button("Cofnij", new Runnable() {
            @Override
            public void run() {
                ticket.setDiscount(false);
                selectDiscount(ticket, c, m, date, hour, s);
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
                    ticket.setCategory(null);
                    ticket.setMovie(null);
                    ticket.setDate(null);
                    ticket.setHour(null);
                    ticket.setSeat(0);
                    ticket.setType(null);
                    ticket.setDiscount(false);
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
        panel.addComponent(discountYes);
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(discountNo);
        panel.addComponent(new EmptySpace());

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));
        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));
        panel.addComponent(new EmptySpace());
        panel.addComponent(backButton);
        panel.addComponent(cancelButton);
        panel.addComponent(new EmptySpace());

        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 8.")));
    }

    public static void selectDiscount(Ticket ticket, Category c, Movie m, String date, String hour, Seat s){
        Panel panel = new Panel(new GridLayout(4));
        GridLayout gridLayout = (GridLayout)panel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        Label title = new Label("Czy posiadasz kod rabatowy?");
        title.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        Button discountYes = new Button("Tak", new Runnable() {
            @Override
            public void run() {
                String input = TextInputDialog.showDialog(gui, "Kod rabatowy", "" +
                        "   Wprowadź tu swój kod rabatowy:  " +
                        "", "");
                if (!input.equals("123")) {
                    MessageDialog.showMessageDialog(gui, "Wiadomość", "\n   Twój kod rabatowy jest niepoprawny :(    \n", MessageDialogButton.OK);
                }
                else {
                    ticket.setDiscount(true);
                    selectTicket(ticket, c, m, date, hour, s);
                }
            }
        });

        discountYes.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        Button discountNo = new Button("No", new Runnable() {
            @Override
            public void run() {
                selectTicket(ticket, c, m, date, hour, s);
            }
        });
        discountNo.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                2,
                1));

        Button backButton = new Button("Cofnij", new Runnable() {
            @Override
            public void run() {
                ticket.setType(null);
                selectPayment(ticket, c, m, date, hour, s);
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
                    ticket.setCategory(null);
                    ticket.setMovie(null);
                    ticket.setDate(null);
                    ticket.setHour(null);
                    ticket.setSeat(0);
                    ticket.setType(null);
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
        panel.addComponent(discountYes);
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(discountNo);
        panel.addComponent(new EmptySpace());

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));
        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(4)));
        panel.addComponent(new EmptySpace());
        panel.addComponent(backButton);
        panel.addComponent(cancelButton);
        panel.addComponent(new EmptySpace());

        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 7.")));
    }

    public static void selectPayment(Ticket ticket, Category c, Movie m, String date, String hour, Seat s){
        if (ticket.getCategory()==null){System.out.println("Kategoria null");}
        else{System.out.println(ticket.getCategory().getName().toString());}
        if (ticket.getMovie()==null){System.out.println("Film null");}
        else{System.out.println(ticket.getMovie().getTitle().toString());}
        System.out.println();

        Panel panel = new Panel(new GridLayout(4));
        GridLayout gridLayout = (GridLayout)panel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        Label title = new Label("Wybierz rodzaj biletu, który Cię interesuje:");
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

        actionListBox.addItem("Normalny     -   30zł", new Runnable() {
            @Override
            public void run() {
                ticket.setType("Normalny");
                selectDiscount(ticket, c, m, date, hour, s);
            }
        });
        actionListBox.addItem("Ulgowy       -   20zł", new Runnable() {
            @Override
            public void run() {
                ticket.setType("Ulgowy");
                selectDiscount(ticket, c, m, date, hour, s);
            }
        });

        Button backButton = new Button("Cofnij", new Runnable() {
            @Override
            public void run() {
                ticket.setSeat(0);
                try {
                    selectMovieSeat(ticket, c, m, date, hour);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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
                    ticket.setCategory(null);
                    ticket.setMovie(null);
                    ticket.setDate(null);
                    ticket.setHour(null);
                    ticket.setSeat(0);
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

        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 6.")));
    }

    public static void selectMovieSeat(Ticket ticket, Category c, Movie m, String date, String hour) throws IOException, SQLException, ParseException {
        if (ticket.getCategory()==null){System.out.println("Kategoria null");}
        else{System.out.println(ticket.getCategory().getName().toString());}
        if (ticket.getMovie()==null){System.out.println("Film null");}
        else{System.out.println(ticket.getMovie().getTitle().toString());}
        System.out.println();

        Panel panel = new Panel(new GridLayout(5));
        GridLayout gridLayout = (GridLayout)panel.getLayoutManager();
        gridLayout.setHorizontalSpacing(0);

        Label title = new Label("Wybierz miejsce, które Cię interesuje:");
        title.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                false,
                false,
                3,
                1));

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(5)));

        panel.addComponent(new EmptySpace());
        panel.addComponent(title);
        panel.addComponent(new EmptySpace());

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(5)));

        Label ekran = new Label(" ----------------------------- \n" +
                "|                             |\n" +
                "|                             |\n" +
                "|        TU JEST EKRAN        |\n" +
                "|                             |\n" +
                "|                             |\n" +
                "`-----------------------------'");
        ekran.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                true,
                false,
                5,
                1));
        ekran.setForegroundColor(TextColor.ANSI.GREEN);
        ekran.setBackgroundColor(TextColor.ANSI.BLACK);
        panel.addComponent(ekran.withBorder(Borders.doubleLineReverseBevel()));
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(5)));
        panel.addComponent(new EmptySpace());
        int i = 1;
        List<Seat> seats = db.getSeats(ticket.getMovie(), ticket.getDate().toString(), ticket.getHour().toString());
        for (Seat s: seats) {
            if (s.isAvaliability() == false) {
                Button button = new Button("MIEJSCE " + s.getSeat(), new Runnable() {
                    @Override
                    public void run() {
                        MessageDialog.showMessageDialog(gui, "Wiadomość", "\n   To miejsce jest już zarezerwowane :(    \n" +
                                "   Spróbuj wybrać inne!    \n", MessageDialogButton.OK);
                    }
                });
                button.setLayoutData(GridLayout.createLayoutData(
                        GridLayout.Alignment.CENTER,
                        GridLayout.Alignment.CENTER,
                        true,
                        false,
                        1,
                        1));

                panel.addComponent(button.withBorder(Borders.singleLineReverseBevel()));
            }

            else {
                Button button = new Button("MIEJSCE " + s.getSeat(), new Runnable() {
                    @Override
                    public void run() {
                        ticket.setSeat(s.getSeat());
                        selectPayment(ticket, c, m, date, hour, s);
                    }
                });
                button.setLayoutData(GridLayout.createLayoutData(
                        GridLayout.Alignment.CENTER,
                        GridLayout.Alignment.CENTER,
                        true,
                        false,
                        1,
                        1));

                panel.addComponent(button.withBorder(Borders.doubleLineReverseBevel()));
            }

            if (i == 3){
                panel.addComponent(new EmptySpace());
                panel.addComponent(new EmptySpace());
            }
            else if (i == 6 ){
                panel.addComponent(new EmptySpace());
            }
            i++;
        }

        Button backButton = new Button("Cofnij", new Runnable() {
            @Override
            public void run() {
                ticket.setHour(null);
                try {
                    selectMovieHour(ticket, c, m, date);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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
                    ticket.setCategory(null);
                    ticket.setMovie(null);
                    ticket.setDate(null);
                    ticket.setHour(null);
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
                2,
                1));

        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(5)));
        panel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(5)));
        panel.addComponent(new EmptySpace());
        panel.addComponent(backButton);
        panel.addComponent(cancelButton);
        panel.addComponent(new EmptySpace());

        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 5.")));
    }

    public static void selectMovieHour(Ticket ticket, Category c, Movie m, String date) throws IOException, SQLException, ParseException {
        if (ticket.getCategory()==null){System.out.println("Kategoria null");}
        else{System.out.println(ticket.getCategory().getName().toString());}
        if (ticket.getMovie()==null){System.out.println("Film null");}
        else{System.out.println(ticket.getMovie().getTitle().toString());}
        System.out.println();

        Panel panel = new Panel(new GridLayout(4));
        GridLayout gridLayout = (GridLayout)panel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        Label title = new Label("Wybierz godzinę, która Cię interesuje:");
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

        List<String> hours = db.getMovieHours(ticket.getMovie(), ticket.getDate().toString());
        for (String h : hours) {
            actionListBox.addItem(h, new Runnable() {
                @Override
                public void run() {
                    ticket.setHour(h);
                    try {
                        selectMovieSeat(ticket, c, m, date, h);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Button backButton = new Button("Cofnij", new Runnable() {
            @Override
            public void run() {
                ticket.setDate(null);
                try {
                    selectMovieDay(ticket, c, m);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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
                    ticket.setCategory(null);
                    ticket.setMovie(null);
                    ticket.setDate(null);
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

        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 4.")));
    }

    public static void selectMovieDay(Ticket ticket, Category c, Movie m) throws IOException, SQLException, ParseException {
        if (ticket.getCategory()==null){System.out.println("Kategoria null");}
        else{System.out.println(ticket.getCategory().getName().toString());}
        if (ticket.getMovie()==null){System.out.println("Film null");}
        else{System.out.println(ticket.getMovie().getTitle().toString());}
        System.out.println();

        Panel panel = new Panel(new GridLayout(4));
        GridLayout gridLayout = (GridLayout)panel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        Label title = new Label("Wybierz dzień, który Cię interesuje:");
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

        List<String> dates = db.getMovieDates(ticket.getMovie());
        for (String d : dates) {
            actionListBox.addItem(d, new Runnable() {
                @Override
                public void run() {
                    try {
                        ticket.setDate(d);
                        selectMovieHour(ticket, c, m, d);
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Button backButton = new Button("Cofnij", new Runnable() {
            @Override
            public void run() {
                ticket.setMovie(null);
                selectMovie(ticket, c);
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
                    ticket.setCategory(null);
                    ticket.setMovie(null);
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


        window.setComponent(panel.withBorder(Borders.doubleLine("Krok 3.")));
    }

    public static void selectMovie(Ticket ticket, Category c) {
        if (ticket.getCategory()==null){System.out.println("Kategoria null");}
        else{System.out.println(ticket.getCategory().getName().toString());}
        if (ticket.getMovie()==null){System.out.println("Film null");}
        else{System.out.println(ticket.getMovie().getTitle().toString());}
        System.out.println();

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

        List<Movie> movies = db.getMovies(c.getName().toString());
        for (Movie m : movies) {
            actionListBox.addItem(m.getTitle(), new Runnable() {
                @Override
                public void run() {
                    try {
                        ticket.setMovie(m);
                        selectMovieDay(ticket, c, m);
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
                    ticket.setCategory(null);

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
        if (ticket.getCategory()==null){System.out.println("Kategoria null");}
        else{System.out.println(ticket.getCategory().getName().toString());}
        if (ticket.getMovie()==null){System.out.println("Film null");}
        else{System.out.println(ticket.getMovie().getTitle().toString());}
        System.out.println();

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
                    selectMovie(ticket, c);
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



