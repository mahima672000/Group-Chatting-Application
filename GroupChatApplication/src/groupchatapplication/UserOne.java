package groupchatapplication;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class UserOne implements ActionListener, Runnable { // ActionListener Interface from java.awt.event we can use it for mouse actions
    
    JTextField text;//so that we can use it globally
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();//frame f
    static DataOutputStream dout;
    
    BufferedReader reader;
    BufferedWriter writer;
    String name = "Harry";
    
    UserOne() {
        
        f.setLayout(null);//we are not using any layout of swing , we will make or own layout
        
        JPanel p1 = new JPanel();//we can create new panels over the frame
        p1.setBackground(new Color(7, 94, 84));//rgb colors for panel
        p1.setBounds(0, 0, 450, 70);//passing cordinates of set bound 0,0 top left ..height 70 lenght 450
        p1.setLayout(null);//only then setBound will run
        f.add(p1);//by sing add fn we can se any componet on top of frame or on anything
        
        //arrow image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));//to pick image from file directory& since we are already inside src folder(User.java is inside src) so we are directly writing icons/3.png
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);//to scale image widht & height are mentioned 
        //if we look above we can see that i1 is an ImageIcon and i2 is Image so first ImageIcon i1 is converted to Image with .getImage()
        //Image i2 now can't be passed to JLabel so for that we will convert it again to ImageIcon i3 
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);//Jlabel class
        back.setBounds(5, 20, 25, 25);//setting coordinates
        p1.add(back);//adding icons on panel p1
        //look here we are callling add fn over p1 and not f ...only then it will be visible
        
        //Arrow click action to close the frame
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);//closing frame
            }
        });
        
        //group icon image
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/groupicon.png"));
        Image i5 = i4.getImage().getScaledInstance(60,60, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 5, 60, 60);
        p1.add(profile);
        
        //video image
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);
        
        //set phone calling image
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);
        
        //set 3 dot image
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);
        
        
        //here is the main use of jlabel it helps in writing anything on top of frame
        JLabel name = new JLabel("Hogwarts Buddies");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);
        
        
        JLabel status = new JLabel("Harry, Hermoine, Ron");
        status.setBounds(110, 35, 160, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(status);
        
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        a1.setBackground(Color.WHITE);
        f.add(a1);
        
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(send);
        
        f.setSize(450, 700);//set size of frame f
        f.setLocation(200, 50);//by default origin of frame is left-topmost we can set it to a new coordinate
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);//frame f got picked by ContetntPane and setBackground used to set color here
        
        f.setVisible(true);//frame is hidden by deafult so setting it to true
        
        try {
            Socket socket = new Socket("localhost", 2003);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void actionPerformed(ActionEvent ae) {//overiding
        try {
            String out = "<html><p>" + name + "</p><p>" + text.getText() + "</p></html>";
            //storing the text(which is inside textfiled) inside string out
            JPanel p2 = formatLabel(out);
            //now append out on panel p2

            a1.setLayout(new BorderLayout());
            //passing border layout inside setLayout 
            
            JPanel right = new JPanel(new BorderLayout());//boder layout for msg
            right.setBackground(Color.WHITE);////text color
            right.add(p2, BorderLayout.LINE_END);//move text to right side
            vertical.add(right); //vertical is box created and it will be aligned to right size
            vertical.add(Box.createVerticalStrut(15));//space between two struts or msgs is 15

            a1.add(vertical, BorderLayout.PAGE_START); //vertical is started from page start

            try {
                writer.write(out);
                writer.write("\r\n");
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

            text.setText("");//before repainting text field is set to empty

            f.repaint();//to call & repaint
            f.invalidate();
            f.validate();   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel(); //creating panel object
        panel.setBackground(Color.WHITE);//background color white
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));//along Y axis
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");//writing out in html format
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));//setting font and font size
        output.setBackground(new Color(37, 211, 102)); //green rgb
        output.setOpaque(true);//only then thr green color background will appear
        output.setBorder(new EmptyBorder(0, 15, 0, 50));
        
        panel.add(output);//adding the output to panel
        
        Calendar cal = Calendar.getInstance(); // using calendar class
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        
        return panel;
    }
    
    public void run() {
        try {
            String msg = "";
            while(true) {
                msg = reader.readLine();
                if (msg.contains(name)) {
                    continue;
                }
                
                JPanel panel = formatLabel(msg);
                
                JPanel left = new JPanel(new BorderLayout());
                left.setBackground(Color.WHITE);
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);
                
                a1.add(vertical, BorderLayout.PAGE_START);
                
                f.repaint();
                f.invalidate();
                f.validate();   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        UserOne one = new UserOne();
        Thread t1 = new Thread(one);
        t1.start();
    }
}