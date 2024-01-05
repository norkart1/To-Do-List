import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;

class Task extends JPanel {

    JLabel index;
    JTextField taskName;
    JButton done;

    Color pink = new Color(255, 161, 161);
    Color green = new Color(188, 226, 158);
    Color doneColor = new Color(233, 119, 119);

    private boolean checked;

    Task() {
        this.setPreferredSize(new Dimension(400, 20));
        this.setBackground(pink);
        this.setLayout(new BorderLayout());

        checked = false;

        index = new JLabel("");
        index.setPreferredSize(new Dimension(20, 20));
        index.setHorizontalAlignment(JLabel.CENTER);
        this.add(index, BorderLayout.WEST);

        taskName = new JTextField("Write Something..");
        taskName.setBorder(BorderFactory.createEmptyBorder());
        taskName.setBackground(pink);

        this.add(taskName, BorderLayout.CENTER);

        done = new JButton("Done");
        done.setPreferredSize(new Dimension(80, 20));
        done.setBorder(BorderFactory.createEmptyBorder());
        done.setBackground(doneColor);
        done.setFocusPainted(false);

        this.add(done, BorderLayout.EAST);
    }

    public void changeIndex(int num) {
        this.index.setText(num + "");
        this.revalidate();
    }

    public JButton getDone() {
        return done;
    }

    public boolean getState() {
        return checked;
    }

    public void changeState() {
        this.setBackground(green);
        taskName.setBackground(green);
        checked = true;
        revalidate();
    }
}

class ToDoList extends JPanel {
    Color lightColor = new Color(39, 227, 94);

    ToDoList() {
        GridLayout layout = new GridLayout(10, 1);
        layout.setVgap(5);

        this.setLayout(layout);
        this.setPreferredSize(new Dimension(400, 560));
        this.setBackground(lightColor);
    }

    public void updateNumber() {
        Component[] listItems = this.getComponents();

        for (int i = 0; i < listItems.length; i++) {
            if (listItems[i] instanceof Task) {
                ((Task) listItems[i]).changeIndex(i + 1);
            }
        }
    }

    public void removeCompletedTasks() {
        for (Component c : getComponents()) {
            if (c instanceof Task) {
                if (((Task) c).getState()) {
                    remove(c);
                    updateNumber();
                }
            }
        }
    }
}

class Footer extends JPanel {
    JButton addTask;
    JButton clear;

    Color orange = new Color(220, 195, 232);
    Color lightColor = new Color(41, 26, 162);
    Border emptyBorder = BorderFactory.createEmptyBorder();

    Footer() {
        this.setPreferredSize(new Dimension(400, 60));
        this.setBackground(lightColor);

        addTask = new JButton("Add Task");
        addTask.setBorder(emptyBorder);
        addTask.setFont(new Font("Sans-serif", Font.ITALIC, 20));
        addTask.setVerticalAlignment(JButton.BOTTOM);
        addTask.setBackground(orange);
        this.add(addTask);

        this.add(Box.createHorizontalStrut(20));

        clear = new JButton("Clear finished tasks");
        clear.setFont(new Font("Sans-serif", Font.ITALIC, 20));
        clear.setBorder(emptyBorder);
        clear.setBackground(orange);
        this.add(clear);
    }

    public JButton getNewTask() {
        return addTask;
    }

    public JButton getClear() {
        return clear;
    }
}

class TitleBar extends JPanel {
    Color lightColor = new Color(252, 221, 176);

    TitleBar() {
        this.setPreferredSize(new Dimension(400, 80));
        this.setBackground(lightColor);
        JLabel titleText = new JLabel(("To Do List"));
        titleText.setPreferredSize(new Dimension(200, 80));
        titleText.setFont(new Font("Sans-serif", Font.ITALIC, 20));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleText);
    }
}

class AppFrame extends JFrame {
    private TitleBar title;
    private Footer footer;
    private ToDoList toDoList;

    private JButton newTask;
    private JButton clear;

    AppFrame() {
        this.setSize(400, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        title = new TitleBar();
        footer = new Footer();
        toDoList = new ToDoList();

        this.add(title, BorderLayout.NORTH);
        this.add(footer, BorderLayout.SOUTH);
        this.add(toDoList, BorderLayout.CENTER);

        newTask = footer.getNewTask();
        clear = footer.getClear();

        addListener();
    }

    public void addListener() {
        newTask.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Task task = new Task();
                toDoList.add(task);
                toDoList.updateNumber();

                task.getDone().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        task.changeState();
                        toDoList.updateNumber();
                        revalidate();
                    }
                });
            }
        });

        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                toDoList.removeCompletedTasks();
                repaint();
            }
        });
    }
}

public class Main {
    public static void main(String[] args) {
        AppFrame frame = new AppFrame();
    }
}
