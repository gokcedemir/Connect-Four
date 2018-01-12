/*
* This program is connect four game. The game of Connect Four is    
* played by two players (computer-user or user1-user2) on a two     
* dimensional board (2D array) with rectangular cells.   
*/
package connectfour;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


/**
 *
 * @author gokce
 */
public class ConnectFour extends JFrame implements ActionListener
{
    public final int SIZE_OF_ARRAY=20;      //max array size
    private JLabel[][] gameCells;      //size*size board array 
    private char userContent;  //X or O
    String gameType;    //P or C
    public char kindOfGame;
    private int row;
    private int column;
    private JFrame frame;
    private JButton[] click;
    private Color player1Color = Color.RED;  // first player
    private Color player2Color = Color.YELLOW;  //second player
    private static int counter = 0;
    private static int firstCount = 0;
    private static int secondCount = 0;
    private static int endCheck = 0; //bitme durumu check etme
    
  
    public ConnectFour()
    {
        row= getBoardSize();   // kullanicidan alinan row ve col
        column = row;
        gameType = getGameType(); //kullanicidan alinan gameType
        frame = new JFrame("connect four");

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setLayout(new GridLayout(row+1, column, 8,8));
        JTextField txtInput = new JTextField("");  //user2 or computer
        
        setUserContent('X');  // baslangıc X ile baslar rengi 
   
        click = new JButton[row];   //butonlar her bir sutun icin
        
        for (int i = 0; i <row; i++)
        {
            click[i]= new JButton();
            click[i].setText(Integer.toString(i+1));
            click[i].setBounds(10,10, 50, 50);
            panel.add(click[i]);
            click[i].addActionListener(this);
            
            
        }
        gameCells = new JLabel[row][column];
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < column; j++)
            {
                gameCells[i][j] = new JLabel();
                gameCells[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                gameCells[i][j].setBorder(new LineBorder(Color.black));
                gameCells[i][j].setOpaque(true);
                gameCells[i][j].setBackground(Color.LIGHT_GRAY);
                panel.add(gameCells[i][j]);
            }
        }
        
         //jframe stuff
        frame.setContentPane(panel);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e)
    {
        for (int i = 0; i<row; i++) 
        {
            for (int j=column-1; j>=0; j--)
            {
                if(gameType.equals("P"))
                {
                    if(e.getActionCommand().equals(Integer.toString(i+1)))
                    {
                        if(gameCells[j][i].getBackground().equals(Color.LIGHT_GRAY))
                        {
                            if(getUserContent() == 'X')  //kirmizi
                            {
                                gameCells[j][i].setBackground(player1Color);
                                setUserContent('O');
                                verticalCheck(player1Color);
                                horizontalCheck(player1Color);
                                crossCheck(player1Color);
                                ++counter;
                                i=row; j=column; break;
                            }
                            else if(getUserContent() == 'O')  //sari
                            {
                                gameCells[j][i].setBackground(player2Color);
                                setUserContent('X');
                                verticalCheck(player2Color);
                                horizontalCheck(player2Color);
                                crossCheck(player2Color);
                                 ++counter;
                                i=row; j=column; break;
                            }
                        }
                        else 
                        {
                            if(getUserContent()=='O')  //sari
                            {
                                if(j-1>=0 && j-1>=0 && j-1< row && gameCells[j-1][i].getBackground().equals(Color.LIGHT_GRAY))
                                {
                                    gameCells[j-1][i].setBackground(player2Color);
                                    setUserContent('X');
                                    verticalCheck(player2Color);
                                    horizontalCheck(player2Color);
                                    crossCheck(player2Color);
                                     ++counter;
                                    i=row; j=column; break;
                                }
                            }
                            else if(getUserContent() =='X')  //kirmizi
                            {
                                if(j-1>=0 && j-1>=0 && j-1< row && gameCells[j-1][i].getBackground().equals(Color.LIGHT_GRAY))
                                {
                                    gameCells[j-1][i].setBackground(player1Color);
                                    setUserContent('O');
                                    verticalCheck(player1Color);
                                    horizontalCheck(player1Color);
                                    crossCheck(player1Color);
                                     ++counter;
                                    i=row; j=column; break;
                                }
                            }
                        }
                 }
                    if(counter == row*column )
                    {
                        JOptionPane.showMessageDialog(null, "Tied Ended :)");
                        i=row; j=column; break;
                    }  
                }
                else if(gameType.equals("C"))
                {
                    if(e.getActionCommand().equals(Integer.toString(i+1)))
                    {
                        if(gameCells[j][i].getBackground().equals(Color.LIGHT_GRAY))
                        {
                           
                                gameCells[j][i].setBackground(player1Color);
                                setUserContent('O');
                                verticalCheck(player1Color);
                                horizontalCheck(player1Color);
                                crossCheck(player1Color);
                                ++counter;
                                
                                if(endCheck ==1)
                                {
                                    i=row; j=column; break;
                                }
                                
                                /*****Computer AI ************/
                                playForComputerAI(); //computer
                                setUserContent('X');
                                verticalCheck(player2Color);
                                horizontalCheck(player2Color);
                                crossCheck(player2Color);
                                ++counter;
                                i=row; j=column; break;
                            
             
                        }
                        if(counter == row*column )
                        {
                            JOptionPane.showMessageDialog(null, "Tied Ended :)");
                            i=row; j=column; break;
                        }  
                    }
                }
            }
        }
    }
    
    private void playForComputerAI()
    {
        int i=0, j=0;
        int count=0;
        int checkFlag=0;

        checkFlag=0;
        for(i=column-1; i>=0; --i)  //dikey ch
        {
            for(j=row-1; j>=0; --j)
            {
                if(gameCells[j][i].getBackground() == player1Color)
                {
                    ++count;
                    if(count==3)
                    {
                        if(j-1>=0 && gameCells[j-1][i].getBackground()== Color.LIGHT_GRAY)
                        {
                            gameCells[j-1][i].setBackground(player2Color);
                            checkFlag=1;
                            i=j=-1; break; //exit nested loop
                        }
                    }
                }
                else if(gameCells[j][i].getBackground()== player2Color)
                {
                    ++count;
                    if(count==3)
                    {
                        if(j-1>=0 && gameCells[j-1][i].getBackground()== Color.LIGHT_GRAY)
                        {
                            gameCells[j-1][i].setBackground(player2Color);
                            checkFlag=1;
                            i=j=-1; break; //exit nested loop
                        }
                    }
                }
            }
            count=0;
        }
        if(checkFlag==0) //yatay ch
        {
            for(i=row-1; i>=0; --i) //yatay ch
            {
                for(j=column-1; j>=0; --j)
                {
                    if(gameCells[i][j].getBackground()== player1Color)
                    {
                        ++count;
                        if(count==3)
                        {
                            if(j-1>=0 && gameCells[i][j-1].getBackground()== Color.LIGHT_GRAY)
                            {
                                if(i-1>=0 && gameCells[i-1][j].getBackground()!= Color.LIGHT_GRAY)
                                {
                                    gameCells[i][j-1].setBackground(player2Color);
                                    i=j=-1; break;
                                }
                                else
                                {
                                    gameCells[i][j-1].setBackground(player2Color);
                                    i=j=-1; break; 
                                }
                            }
                            else if(j+1<column && gameCells[i][j+1].getBackground()== Color.LIGHT_GRAY)
                            {
                                if(i-1>=0 && gameCells[i-1][j].getBackground()!= Color.LIGHT_GRAY)
                                {
                                    gameCells[i][j+1].setBackground(player2Color);
                                    i=j=-1; break;
                                }
                            }
                        }
                        else
                        {
                            if(j-1>=0 && gameCells[i][j-1].getBackground()== Color.LIGHT_GRAY)
                            {
                                if(i+1<row && gameCells[i+1][j-1].getBackground()!= Color.LIGHT_GRAY)
                                {
                                    gameCells[i][j-1].setBackground(player2Color);
                                    i=j=-1; break;
                                }
                            }
                            if(j+1<column && gameCells[i][j+1].getBackground()== Color.LIGHT_GRAY)
                            {
                                if(i+1<row && gameCells[i+1][j+1].getBackground()!= Color.LIGHT_GRAY )
                                {
                                    gameCells[i][j+1].setBackground(player2Color);
                                    i=j=-1; break;
                                }
                                else if(i+1==row)
                                {
                                    gameCells[i][j+1].setBackground(player2Color);
                                    i=j=-1; break;
                                }
                            }
                            if(i-1>=0 && gameCells[i-1][j].getBackground()== Color.LIGHT_GRAY)
                            {
                                gameCells[i-1][j].setBackground(player2Color);
                                i=j=-1; break;
                            }
                        }
                    }
                    else
                        count=0;
                }
                count=0;
            }
        }
    }

    public int getBoardSize()
    {
        String str = JOptionPane.showInputDialog(null,"Select your board size: ", JOptionPane.QUESTION_MESSAGE);
        while(Integer.parseInt(str) <4)
        {
           str= JOptionPane.showInputDialog(null,"Board size should be minimum 4: ", JOptionPane.ERROR_MESSAGE);
        }
        return (Integer.parseInt(str));
    }

    /**
     *
     * @return game type as a string.
     */
    public String getGameType()
    {
        gameType=JOptionPane.showInputDialog(null,"Select your game type(P or C): ", JOptionPane.QUESTION_MESSAGE);
        while(!(gameType.equals("P") || gameType.equals("C")))
        {
            gameType=JOptionPane.showInputDialog(null,"(P or C): ", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, "Game is starting..");
        return gameType;
    }

    /**
     *
     * @return user cotnent information as a char. user vs computer, or user vs user 
     */
    public char getUserContent() 
    {
        return userContent;
    }

    public void setUserContent(char content)
    {
        userContent = content;
    }

    /**
     * Enable button if column is available.
     */
    public void enableButtons()
    {
        for (int i = 0; i < getBoardSize(); i++)
        {
            click[i].setEnabled(true);
        }
    }
    
    /**
     * Disable button if column is not available.
     * @param numberbutton number of button
     */
    public void disableButtons(int numberbutton)
    {
        click[numberbutton].setEnabled(false);
    }
    public void verticalCheck(Color clr) //dikey
    {
        int temp= 0;
        int i=0;
        int j=0;
        int k=0;

        for(i=0; i<column; ++i) // ching four 
        {
            for(j=0; j<row; ++j)
            {
                if(gameCells[j][i].getBackground() == clr) //dikey ch
                {
                    ++temp;
                    if(temp == 4)
                    {
                        if("P".equals(gameType) && clr==player1Color)
                        {
                            JOptionPane.showMessageDialog(null, "Player1 Won..");
                            endCheck =1;
                            i=j=column; break; 
                        }
                        else if(gameType.equals("P") && clr==player2Color)
                        {
                            JOptionPane.showMessageDialog(null,"Player2 Won..");
                            endCheck =1;
                            i=j=column; break;
                        }
                        else if("C".equals(gameType) && clr==player1Color)
                        {
                            JOptionPane.showMessageDialog(null, "Player1 Won..");
                            endCheck =1;
                            i=j=column; break;
                        }
                        else if("C".equals(gameType) && clr==player2Color)
                        {
                            JOptionPane.showMessageDialog(null, "Computer Won..");
                            endCheck = 1;
                            i=j=column; break;
                        }
                    }                       
                }
                else 
                    temp=0;
            }
            temp=0;
        }
    }
    
    private void horizontalCheck(Color clr)  //yatay check
    {
        int i=0, j=0, temp=0;

        for(i=0; i<row; ++i) // checking four
        {
            for(j=0; j<column; ++j)
            {
                if(gameCells[i][j].getBackground() == clr) //yatay ch
                {
                    ++temp;
                    if(temp == 4)
                    {
                        if(clr== player1Color)
                        {
                            JOptionPane.showMessageDialog(null, "Player1 Won..");
                            endCheck =1;
                            i=j=column; break;
                        }
                        else if(clr== player2Color && gameType.equals("P"))
                        {
                            JOptionPane.showMessageDialog(null, "Player2 Won..");
                            endCheck =1;
                            i=j=column; break;
                        }
                        else if(clr== player1Color)
                        {
                           JOptionPane.showMessageDialog(null, "Player1 Won..");
                           endCheck =1;
                           i=j=column; break;
                        }
                        else if(clr== player2Color && gameType.equals("C"))
                        {
                            JOptionPane.showMessageDialog(null, "Computer Won..");
                            endCheck =1;
                            i=j=column; break;
                        }
                    }                       
                }
                else 
                    temp=0;
            }
            temp=0;
        }
    }

    private void crossCheck(Color clr)
    {
        int temp=0;

        temp=0;
        for (int i = row-1; i>=0; --i)  //sağ capraz
        {
            for (int j = 0; j< column; ++j)
            {
                if(gameCells[i][j].getBackground() == clr)
                {
                    for(int k=0; k<column; ++k ) //sag capraz
                    {
                        if(i-k>=0 && j+k<column && gameCells[i-k][j+k].getBackground() == clr)
                        {
                            ++temp;
                            if(temp==4)
                            {
                                if(clr== player1Color)
                                {
                                    JOptionPane.showMessageDialog(null, "Player1 Won..");
                                    endCheck =1;
                                    i=row; j=-1; break;
                                }
                                else if(clr== player2Color && gameType.equals("P"))
                                {
                                    JOptionPane.showMessageDialog(null, "Player2 Won..");
                                    endCheck =1;
                                    i=row; j=-1; break;
                                }
                                else if(clr== player1Color)
                                {
                                    JOptionPane.showMessageDialog(null,"Player1 Won..");
                                    endCheck =1;
                                    i=row; j=-1; break;
                                }
                                else if(clr== player2Color && gameType.equals("C"))
                                {
                                    JOptionPane.showMessageDialog(null,"Computer Won..");
                                    endCheck =1;
                                    i=row; j=-1; break;
                                }
                            }
                        }
                        else
                            temp=0;
                    }
                    temp=0;
                }
                else
                    temp=0;
            }
        }

        temp=0;
        for (int i = row-1; i>=0; --i)  //sol capraz
        {
            for (int j=column-1 ; j>=0 ; --j)
            {
                if(gameCells[i][j].getBackground() == clr)
                {
                    for(int k=0; k<column; ++k )
                    {
                        if( i-k>=0 && j-k>=0 && gameCells[i-k][j-k].getBackground() == clr)
                        {
                            ++temp;
                            if(temp==4)
                            {
                                if(clr== player1Color)
                                {
                                    JOptionPane.showMessageDialog(null, "Player1 Won..");
                                    endCheck =1;
                                    i=j=-1; break;
                                }
                                else if(clr== player2Color && gameType.equals("P"))
                                {
                                    JOptionPane.showMessageDialog(null, "Player2 Won..");
                                    endCheck =1;
                                    i=j=-1; break;
                                }
                                else if(clr== player1Color)
                                {
                                    JOptionPane.showMessageDialog(null,"Player1 Won..");
                                    endCheck =1;
                                    i=j=-1; break;
                                }
                                else if(clr== player2Color && gameType.equals("C"))
                                {
                                    JOptionPane.showMessageDialog(null,"Computer Won..");
                                    endCheck =1;
                                    i=j=-1; break;
                                }
                            }
                        }
                        else
                            temp=0;
                    }
                    temp=0;
                }
                else
                    temp=0;
            }
        }
    }
    
    public static void main(String[] args)
    {
        ConnectFour maintest = new ConnectFour();
        
    }
    
}


