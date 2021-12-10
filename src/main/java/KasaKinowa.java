import models.Category;
import models.Movie;
import models.Seat;
import models.Ticket;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.awt.Dimension;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Container;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.*;

import javax.swing.JFrame;
import java.awt.*;
import java.util.*;
import java.applet.Applet;

class Confirming extends JPanel implements ActionListener {
    private JButton startButton;
    private JFrame mainFrame;
    private Database database;

    public Confirming(JFrame frame, Database database) throws IOException {
        super();
        mainFrame = frame;
        this.database = database;
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);

        setPreferredSize(new Dimension(200, 100));
        setVisible(true);

        createComponents();
    }

    private void createComponents() throws IOException {
        int fontSize = 16;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        JLabel title = new JLabel("Dziękujemy za zakup biletu!");
        title.setFont(font);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel title2 = new JLabel("Po wrzuceniu monet, Twój bilet zostanie wydrukowany.");
        title2.setFont(font);
        title2.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel title3 = new JLabel("Życzymy miłego seansu! :)");
        title3.setFont(font);
        title3.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel title4 = new JLabel("Kliknij poniżej, aby rozpocząć nową transakcję.");
        title4.setFont(font);
        title4.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        BufferedImage myPicture = ImageIO.read(new File("src/main/java/see ya.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));

        startButton = new JButton("Rozpocznij nową transakcję");
        startButton.addActionListener(this);

        JSeparator sep = new JSeparator();
        sep.setVisible(false);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(6, 1));
        startPanel.add(title);
        startPanel.add(title2);
        startPanel.add(title3);
        startPanel.add(title4);
        startPanel.add(sep);
        startPanel.add(startButton);


        mainPanel.add(startPanel, BorderLayout.NORTH);
        mainPanel.add(picLabel, BorderLayout.CENTER);

        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Ticket ticket = new Ticket();
        JPanel categoryPanel = new SelectCategory(mainFrame, database, ticket);
        setVisible(false);
        mainFrame.add(categoryPanel);
    }
}

class SelectTicket extends JPanel implements ActionListener {
    private JFrame mainFrame;
    private Ticket ticket;
    private Database database;
    private Category category;
    private Movie movie;
    private String date;
    private String hour;
    private Seat seat;
    private String type;
    private Boolean discount;

    public SelectTicket (JFrame frame, Database database, Ticket ticket, Category category, Movie movie, String date, String hour, Seat seat, String type, Boolean discount) throws SQLException, IOException {
        super();
        mainFrame = frame;
        this.database = database;
        this.ticket = ticket;
        this.category = category;
        this.movie = movie;
        this.date = date;
        this.hour = hour;
        this.seat = seat;
        this.type = type;
        this.discount = discount;
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);

        createComponents();
    }

    private void createComponents() throws SQLException, IOException {
        System.out.println(ticket.getCategory());
        System.out.println(ticket.getMovie());
        System.out.println(ticket.getDate());
        System.out.println(ticket.getHour());
        System.out.println(ticket.getSeat());
        System.out.println(ticket.getType());
        System.out.println(ticket.isDiscount());
        Font font2 = new Font("Arial", Font.BOLD, 12);
        JLabel stepLabel = new JLabel("Krok 9.");
        stepLabel.setFont(font2);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Sprawdź, czy dane na Twoim bilecie się zgadzają:");
        titleLabel.setFont(font);

        JSeparator sep = new JSeparator();
        sep.setVisible(false);
        JSeparator sep2 = new JSeparator();
        sep2.setVisible(false);

        JPanel mainPanel = new JPanel();

        Font font3 = new Font("Arial", Font.BOLD, 12);

        JPanel moviePanel = new JPanel();
        moviePanel.setAlignmentX(CENTER_ALIGNMENT);
        moviePanel.setLayout(new GridLayout(1, 2, 10, 0));
        JLabel movieLabel = new JLabel("TYTUŁ:");
        movieLabel.setFont(font3);
        movieLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel movieLabel2 = new JLabel(ticket.getMovie().getTitle());
        movieLabel2.setFont(font3);
        moviePanel.add(movieLabel);
        moviePanel.add(movieLabel2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        JPanel datePanel = new JPanel();
        datePanel.setAlignmentX(CENTER_ALIGNMENT);
        datePanel.setLayout(new GridLayout(1, 2, 10, 0));
        JLabel dateLabel = new JLabel("DATA:");
        dateLabel.setFont(font3);
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel dateLabel2 = new JLabel(ticket.getDate());
        dateLabel2.setFont(font3);
        datePanel.add(dateLabel);
        datePanel.add(dateLabel2);

        JPanel hourPanel = new JPanel();
        hourPanel.setAlignmentX(CENTER_ALIGNMENT);
        hourPanel.setLayout(new GridLayout(1, 2, 10, 0 ));
        JLabel hourLabel = new JLabel("GODZINA:");
        hourLabel.setFont(font3);
        hourLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel hourLabel2 = new JLabel(ticket.getHour());
        dateLabel2.setFont(font3);
        hourPanel.add(hourLabel);
        hourPanel.add(hourLabel2);

        JPanel roomPanel = new JPanel();
        roomPanel.setAlignmentX(CENTER_ALIGNMENT);
        roomPanel.setLayout(new GridLayout(1, 2, 10, 0 ));
        JLabel roomLabel = new JLabel("SALA:");
        roomLabel.setFont(font3);
        roomLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel roomLabel2 = new JLabel(String.valueOf(ticket.getRoom()));
        roomLabel2.setFont(font3);
        roomPanel.add(roomLabel);
        roomPanel.add(roomLabel2);

        JPanel seatPanel = new JPanel();
        seatPanel.setAlignmentX(CENTER_ALIGNMENT);
        seatPanel.setLayout(new GridLayout(1, 2, 10, 0 ));
        JLabel seatLabel = new JLabel("MIEJSCE:");
        seatLabel.setFont(font3);
        seatLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel seatLabel2 = new JLabel(String.valueOf(ticket.getSeat()));
        roomLabel2.setFont(font3);
        seatPanel.add(seatLabel);
        seatPanel.add(seatLabel2);

        JPanel pricePanel = new JPanel();
        pricePanel.setAlignmentX(CENTER_ALIGNMENT);
        pricePanel.setLayout(new GridLayout(1, 2, 10, 0 ));
        JLabel priceLabel = new JLabel("CENA:");
        priceLabel.setFont(font3);
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel priceLabel2 = new JLabel(String.valueOf(ticket.calculatePrice()));
        priceLabel2.setFont(font3);
        pricePanel.add(priceLabel);
        pricePanel.add(priceLabel2);

        JButton confirmButton = new JButton("Zatwierdź");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    database.confirmTicket(ticket);
                    setVisible(false);
                    mainFrame.add(new Confirming(mainFrame, database));
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton backButton = new JButton("Cofnij");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticket.setDiscount(false);
                setVisible(false);
                try {
                    mainFrame.add(new SelectDiscount(mainFrame, database, ticket, category, movie, date, hour, seat, type));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton cancelButton = new JButton("Anuluj zakup");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    mainFrame.add(new StartPanel(mainFrame, database));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 6));

        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setLayout(new GridLayout(12, 1, 10, 1));

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(stepLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(sep);
        mainPanel.add(moviePanel);
        mainPanel.add(datePanel);
        mainPanel.add(hourPanel);
        mainPanel.add(roomPanel);
        mainPanel.add(seatPanel);
        mainPanel.add(pricePanel);
        mainPanel.add(sep2);
        mainPanel.add(confirmButton);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}

class PassCode extends JPanel implements ActionListener {
    private JFrame mainFrame;
    private Ticket ticket;
    private Database database;
    private Category category;
    private Movie movie;
    private String date;
    private String hour;
    private Seat seat;
    private String type;

    public PassCode (JFrame frame, Database database, Ticket ticket, Category category, Movie movie, String date, String hour, Seat seat, String type) throws SQLException, IOException {
        super();
        mainFrame = frame;
        this.database = database;
        this.ticket = ticket;
        this.category = category;
        this.movie = movie;
        this.date = date;
        this.hour = hour;
        this.seat = seat;
        this.type = type;
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);
        createComponents();
    }

    private void createComponents() throws SQLException, IOException {
        Font font2 = new Font("Arial", Font.BOLD, 12);
        JLabel stepLabel = new JLabel("Krok 8.");
        stepLabel.setFont(font2);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Podaj swój kod rabatowy:");
        titleLabel.setFont(font);

        JSeparator sep = new JSeparator();
        sep.setVisible(false);
        JSeparator sep2 = new JSeparator();
        sep2.setVisible(false);

        JPanel mainPanel = new JPanel();
        String password = "1234";
        JPasswordField passField = new JPasswordField();

        JButton backButton = new JButton("Anuluj");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    mainFrame.add(new SelectDiscount(mainFrame, database, ticket, category, movie, date, hour, seat, type));
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton cancelButton = new JButton("Zatwierdź");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (password.equals(passField.getText())) {
                    ticket.setDiscount(true);
                    setVisible(false);
                    try {
                        mainFrame.add(new SelectTicket(mainFrame, database, ticket, category, movie, date, hour, seat, type, true));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(mainPanel, "\n    Twój kod rabatowy jest niepoprawny :(   \n");
                    setVisible(false);
                    try {
                        mainFrame.add(new SelectDiscount(mainFrame, database, ticket, category, movie, date, hour, seat, type));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 40));

        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setLayout(new GridLayout(6, 1, 10, 6));

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(stepLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(sep);
        mainPanel.add(passField);
        mainPanel.add(sep2);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}

class SelectDiscount extends JPanel implements ActionListener {
    private JFrame mainFrame;
    private Ticket ticket;
    private Database database;
    private Category category;
    private Movie movie;
    private String date;
    private String hour;
    private Seat seat;
    private String type;

    public SelectDiscount (JFrame frame, Database database, Ticket ticket, Category category, Movie movie, String date, String hour, Seat seat, String type) throws SQLException, IOException {
        super();
        mainFrame = frame;
        this.database = database;
        this.ticket = ticket;
        this.category = category;
        this.movie = movie;
        this.date = date;
        this.hour = hour;
        this.seat = seat;
        this.type = type;
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);
        createComponents();
    }

    private void createComponents() throws SQLException, IOException {
        Font font2 = new Font("Arial", Font.BOLD, 12);
        JLabel stepLabel = new JLabel("Krok 8.");
        stepLabel.setFont(font2);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Czy posiadasz kod rabatowy?");
        titleLabel.setFont(font);

        JSeparator sep = new JSeparator();
        sep.setVisible(false);
        JSeparator sep2 = new JSeparator();
        sep2.setVisible(false);

        JButton backButton = new JButton("Cofnij");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ticket.setType(null);
                try {
                    mainFrame.add(new SelectPayment(mainFrame, database, ticket, category, movie, date, hour, seat));
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton cancelButton = new JButton("Anuluj zakup");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    mainFrame.add(new StartPanel(mainFrame, database));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel mainPanel = new JPanel();

        JButton yesButton = new JButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               setBackground(Color.green);
               setVisible(false);
                try {
                    mainFrame.add(new PassCode(mainFrame, database, ticket, category, movie, date, hour, seat, type));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton noButton = new JButton("No");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticket.setDiscount(false);
                setVisible(false);
                try {
                    mainFrame.add(new SelectTicket(mainFrame, database, ticket, category, movie, date, hour, seat, type, false));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 40));

        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setLayout(new GridLayout(7, 1, 10, 6));

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(stepLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(sep);
        mainPanel.add(yesButton);
        mainPanel.add(noButton);
        mainPanel.add(sep2);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}

class SelectPayment extends JPanel implements ActionListener {
    private JFrame mainFrame;
    private Ticket ticket;
    private Database database;
    private Category category;
    private Movie movie;
    private String date;
    private String hour;
    private Seat seat;

    public SelectPayment (JFrame frame, Database database, Ticket ticket, Category category, Movie movie, String date, String hour, Seat seat) throws SQLException, IOException {
        super();
        mainFrame = frame;
        this.database = database;
        this.ticket = ticket;
        this.category = category;
        this.movie = movie;
        this.date = date;
        this.hour = hour;
        this.seat = seat;
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);
        createComponents();
    }

    private void createComponents() throws SQLException, IOException {
        Font font2 = new Font("Arial", Font.BOLD, 12);
        JLabel stepLabel = new JLabel("Krok 7.");
        stepLabel.setFont(font2);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Wybierz rodzaj biletu, który Cię interesuje:");
        titleLabel.setFont(font);

        JSeparator sep = new JSeparator();
        sep.setVisible(false);
        JSeparator sep2 = new JSeparator();
        sep2.setVisible(false);

        JButton backButton = new JButton("Cofnij");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ticket.setSeat(0);
                ticket.setRoom(0);
                try {
                    mainFrame.add(new SelectMovieSeat(mainFrame, database, ticket, category, movie, date, hour));
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton cancelButton = new JButton("Anuluj zakup");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    mainFrame.add(new StartPanel(mainFrame, database));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton halfPriceButton = new JButton("Ulgowy       -   20zł");
        halfPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ticket.setType("Ulgowy");
                try {
                    mainFrame.add(new SelectDiscount(mainFrame, database, ticket, category, movie, date, hour, seat, "Ulgowy"));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton normalButton = new JButton("Normalny     -   30zł");
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ticket.setType("Normalny");
                setVisible(false);
                try {
                    mainFrame.add(new SelectDiscount(mainFrame, database, ticket, category, movie, date, hour, seat, "Normalny"));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 40));

        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setLayout(new GridLayout(7, 1, 10, 6));

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(stepLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(sep);
        mainPanel.add(halfPriceButton);
        mainPanel.add(normalButton);
        mainPanel.add(sep2);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}

class SelectMovieSeat extends JPanel implements ActionListener {
    private JFrame mainFrame;
    private Ticket ticket;
    private Database database;
    private Category category;
    private Movie movie;
    private String date;
    private String hour;

    public SelectMovieSeat (JFrame frame, Database database, Ticket ticket, Category category, Movie movie, String date, String hour) throws SQLException, IOException {
        super();
        mainFrame = frame;
        this.database = database;
        this.ticket = ticket;
        this.category = category;
        this.movie = movie;
        this.date = date;
        this.hour = hour;
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);
        createComponents();
    }

    private void createComponents() throws SQLException, IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        List<Seat> seats = database.getSeats(ticket.getMovie(), ticket.getDate().toString(), ticket.getHour().toString());
        Font font2 = new Font("Arial", Font.BOLD, 12);
        JLabel stepLabel = new JLabel("Krok 5.");
        stepLabel.setFont(font2);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Wybierz miejsce, które Cię interesuje:");
        titleLabel.setFont(font);

        JSeparator sep = new JSeparator();
        sep.setVisible(false);
        JSeparator sep2 = new JSeparator();
        sep2.setVisible(false);
        JSeparator sep3 = new JSeparator();
        sep3.setVisible(false);

        JButton backButton = new JButton("Cofnij");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ticket.setHour(null);
                try {
                    mainFrame.add(new SelectMovieHour(mainFrame, database, ticket, category, movie, date));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton cancelButton = new JButton("Anuluj zakup");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    mainFrame.add(new StartPanel(mainFrame, database));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 40));

        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setLayout(new GridLayout(9, 1, 10, 6));

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(stepLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(sep);

//        BufferedImage myPicture = ImageIO.read(new File("src/main/java/cinema screen.jpg"));
//        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
//        mainPanel.add(picLabel);
//        mainPanel.add(sep3);

        JPanel seatPanel1 = new JPanel();
        seatPanel1.setAlignmentX(CENTER_ALIGNMENT);
        seatPanel1.setLayout(new GridLayout(1, 3, 10, 10));
        JPanel seatPanel2 = new JPanel();
        seatPanel2.setAlignmentX(CENTER_ALIGNMENT);
        seatPanel2.setLayout(new GridLayout(1, 3, 10, 10));
        JPanel seatPanel3 = new JPanel();
        seatPanel3.setAlignmentX(CENTER_ALIGNMENT);
        seatPanel3.setLayout(new GridLayout(1, 3, 10, 10));
        int seatCounter = 0;
        for (Seat seat : seats) {
            seatCounter++;
            JButton categoryButton = new JButton(String.valueOf(seat.getSeat()));
            if (seat.isAvaliability() == false) {
                categoryButton.setForeground(Color.red);
                categoryButton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JOptionPane.showMessageDialog(mainPanel, "\n   To miejsce jest już zarezerwowane :(    \n" +
                                "   Spróbuj wybrać inne!    \n" );
                    }
                });
            }
            else {
                categoryButton.setForeground(Color.green);
                categoryButton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                    ticket.setSeat(seat.getSeat());
                    ticket.setRoom(seat.getRoom());
                    setVisible(false);
                        try {
                            mainFrame.add(new SelectPayment(mainFrame, database, ticket, category, movie, date, hour, seat));
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }

            if (seatCounter >= 1 && seatCounter <= 3) seatPanel1.add(categoryButton);
            else if (seatCounter >= 4 && seatCounter <= 6) seatPanel2.add(categoryButton);
            else if (seatCounter >= 7 && seatCounter <= 9) seatPanel3.add(categoryButton);
            if (seatCounter == 3) mainPanel.add(seatPanel1);
            else if (seatCounter == 6) mainPanel.add(seatPanel2);
            else if (seatCounter == 9) mainPanel.add(seatPanel3);
        }
        mainPanel.add(sep2);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}

class SelectMovieHour extends JPanel implements ActionListener {
    private JFrame mainFrame;
    private Ticket ticket;
    private Database database;
    private Category category;
    private Movie movie;
    private String date;

    public SelectMovieHour (JFrame frame, Database database, Ticket ticket, Category category, Movie movie, String date) throws SQLException {
        super();
        mainFrame = frame;
        this.database = database;
        this.ticket = ticket;
        this.category = category;
        this.movie = movie;
        this.date = date;
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);
        createComponents();
    }

    private void createComponents() throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        List<String> hours = database.getMovieHours(ticket.getMovie(), ticket.getDate().toString());
        Font font2 = new Font("Arial", Font.BOLD, 12);
        JLabel stepLabel = new JLabel("Krok 4.");
        stepLabel.setFont(font2);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Wybierz godzinę, który Cię interesuje:");
        titleLabel.setFont(font);

        JSeparator sep = new JSeparator();
        sep.setVisible(false);
        JSeparator sep2 = new JSeparator();
        sep2.setVisible(false);

        JButton backButton = new JButton("Cofnij");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ticket.setDate(null);
                try {
                    mainFrame.add(new SelectMovieDay(mainFrame, database, ticket, category, movie));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton cancelButton = new JButton("Anuluj zakup");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    mainFrame.add(new StartPanel(mainFrame, database));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 40));

        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setLayout(new GridLayout(5 + hours.size(), 1, 10, 6));

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(stepLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(sep);

        for (String hour : hours) {
            JButton categoryButton = new JButton(hour);
            categoryButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    ticket.setHour(hour);
                    setVisible(false);
                    try {
                        mainFrame.add(new SelectMovieSeat(mainFrame, database, ticket, category, movie, date, hour));
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            mainPanel.add(categoryButton);
        }

        mainPanel.add(sep2);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}

class SelectMovieDay extends JPanel implements ActionListener {
    private JFrame mainFrame;
    private Ticket ticket;
    private Database database;
    private Category category;
    private Movie movie;

    public SelectMovieDay(JFrame frame, Database database, Ticket ticket, Category category, Movie movie) throws SQLException {
        super();
        mainFrame = frame;
        this.database = database;
        this.ticket = ticket;
        this.category = category;
        this.movie = movie;
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);
        createComponents();
    }

    private void createComponents() throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        List<String> dates = database.getMovieDates(ticket.getMovie());
        Font font2 = new Font("Arial", Font.BOLD, 12);
        JLabel stepLabel = new JLabel("Krok 3.");
        stepLabel.setFont(font2);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Wybierz dzień, który Cię interesuje:");
        titleLabel.setFont(font);

        JSeparator sep = new JSeparator();
        sep.setVisible(false);
        JSeparator sep2 = new JSeparator();
        sep2.setVisible(false);

        JButton backButton = new JButton("Cofnij");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ticket.setMovie(null);
                mainFrame.add(new SelectMovie(mainFrame, database, ticket, category));
            }
        });
        JButton cancelButton = new JButton("Anuluj zakup");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    mainFrame.add(new StartPanel(mainFrame, database));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 40));

        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setLayout(new GridLayout(5 + dates.size(), 1, 10, 6));

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(stepLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(sep);

        for (String date : dates) {
            JButton categoryButton = new JButton(date);
            categoryButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    ticket.setDate(date);
                    setVisible(false);
                    try {
                        mainFrame.add(new SelectMovieHour(mainFrame, database, ticket, category, movie, date));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            mainPanel.add(categoryButton);
        }

        mainPanel.add(sep2);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}

class SelectMovie extends JPanel implements ActionListener {
    private JFrame mainFrame;
    private Ticket ticket;
    private Database database;
    private Category category;

    public SelectMovie(JFrame frame, Database database, Ticket ticket, Category category) {
        super();
        mainFrame = frame;
        this.database = database;
        this.ticket = ticket;
        this.category = category;
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);
        createComponents();
    }

    private void createComponents() {
        List<Movie> movies = database.getMovies(category.getName().toString());
        Font font2 = new Font("Arial", Font.BOLD, 12);
        JLabel stepLabel = new JLabel("Krok 2.");
        stepLabel.setFont(font2);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Wybierz film, który Cię interesuje:");
        titleLabel.setFont(font);

        JSeparator sep = new JSeparator();
        sep.setVisible(false);
        JSeparator sep2 = new JSeparator();
        sep2.setVisible(false);

        JButton backButton = new JButton("Cofnij");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ticket.setCategory(null);
                mainFrame.add(new SelectCategory(mainFrame, database, ticket));
            }
        });
        JButton cancelButton = new JButton("Anuluj zakup");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    mainFrame.add(new StartPanel(mainFrame, database));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 40));

        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setLayout(new GridLayout(5 + movies.size(), 1, 10, 6));

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(stepLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(sep);

        for (Movie movie : movies) {
            JButton categoryButton = new JButton(movie.getTitle());
            categoryButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    ticket.setMovie(movie);
                    try {
                        setVisible(false);
                        mainFrame.add(new SelectMovieDay(mainFrame, database, ticket, category, movie));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            mainPanel.add(categoryButton);
        }

        mainPanel.add(sep2);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}

class SelectCategory extends JPanel implements ActionListener {
    private JFrame mainFrame;
    private Ticket ticket;
    private Database database;

    public SelectCategory(JFrame frame, Database database, Ticket ticket) {
        super();
        mainFrame = frame;
        this.ticket = ticket;
        this.database = database;
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);
        createComponents();
    }

    private void createComponents() {
        List<Category> categories = database.getCategories();
        Font font2 = new Font("Arial", Font.BOLD, 12);
        JLabel stepLabel = new JLabel("Krok 1.");
        stepLabel.setFont(font2);
        stepLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("Arial", Font.BOLD, 20);
        JLabel titleLabel = new JLabel("Wybierz kategorię, która Cię interesuje:");
        titleLabel.setFont(font);

        JSeparator sep = new JSeparator();
        sep.setVisible(false);
        JSeparator sep2 = new JSeparator();
        sep2.setVisible(false);

        JButton backButton = new JButton("Cofnij");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    mainFrame.add(new StartPanel(mainFrame, database));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JButton cancelButton = new JButton("Anuluj zakup");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                try {
                    mainFrame.add(new StartPanel(mainFrame, database));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 40));

        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.setLayout(new GridLayout(5 + categories.size(), 1, 10, 6));

        mainPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(stepLabel);
        mainPanel.add(titleLabel);
        mainPanel.add(sep);

        for (Category category : categories) {
            JButton categoryButton = new JButton(category.getName());
            categoryButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    setBackground(Color.blue);
                    setVisible(false);
                    ticket.setCategory(category);
                    mainFrame.add(new SelectMovie(mainFrame, database, ticket, category));
                }
            });
            mainPanel.add(categoryButton);
        }

        mainPanel.add(sep2);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}

class StartPanel extends JPanel implements ActionListener {
    private JButton startButton;
    private JFrame mainFrame;
    private Database database;

    public StartPanel(JFrame frame, Database database) throws IOException {
        super();
        mainFrame = frame;
        this.database = database;
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        gridBag.setConstraints(this, constraints);
        setLayout(gridBag);

        setPreferredSize(new Dimension(200, 100));
        setVisible(true);

        createComponents();
    }

    private void createComponents() throws IOException {
        int fontSize = 26;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        JLabel title = new JLabel("Kliknij, aby rozpocząć zakup biletu:");
        title.setFont(font);

        startButton = new JButton("Rozpocznij");
        startButton.addActionListener(this);

        JSeparator sep = new JSeparator();
        sep.setVisible(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(3, 1));
        startPanel.add(title);
        startPanel.add(sep);
        startPanel.add(startButton);

        BufferedImage myPicture = ImageIO.read(new File("src/main/java/kino-removebg-preview.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        mainPanel.add(picLabel, BorderLayout.NORTH);
        mainPanel.add(startPanel, BorderLayout.CENTER);
//        mainPanel.add(picLabel);
//        this.add(picLabel);
        this.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Ticket ticket = new Ticket();
        JPanel categoryPanel = new SelectCategory(mainFrame, database, ticket);
        setVisible(false);
        mainFrame.add(categoryPanel);
    }
}

class StartFrame extends JFrame {
    public StartFrame() throws IOException {
        super("Kasa kinowa");
        Database database = new Database();
        JPanel mainPanel = new StartPanel(this, database);
        add(mainPanel);

        setPreferredSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}

public class KasaKinowa {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new StartFrame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}



