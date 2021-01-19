/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;

/**
 *
 * @author Fatima Adel
 */
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class FIFO {

    boolean flg, hit, hit2;
    ArrayList<String> refrencesList;
    int row, col, pfi, phi, k, numberOfPages, f;
    Object Pages[][];
    DefaultTableModel MainMemory, VirtualMemory;
    String pageState,in="",out="";

    public FIFO(DefaultTableModel list1, DefaultTableModel list2, ArrayList<String> ref, boolean hi) {
        VirtualMemory = list1;
        numberOfPages = 8;
        hit = false;
        hit2 = hi;
        refrencesList = new ArrayList<>();
        MainMemory = list2;
        refrencesList.addAll(ref);
        set();
        if (!refrencesList.isEmpty()) {
            GetData();
        }
    }

    private void set() {
        col = 1;
        row = numberOfPages;
        row++;
    }

    public void fifoProcess(String Pagetxt) {
        boolean ph = false;
        String[][] oldMain = new String[MainMemory.getRowCount()][2];
        for (int i = 0; i < oldMain.length; i++) {
            for (int j = 0; j < oldMain[i].length; j++) {
                oldMain[i][j] = MainMemory.getValueAt(i, j).toString();
            }
        }

        for (int j = 0; j < VirtualMemory.getRowCount(); j++) {
            if (Pagetxt.equals(VirtualMemory.getValueAt(j, 0))) {
                if (VirtualMemory.getValueAt(j, 1).equals("")) {
                    ph = false;
                    if (MainMemory.getRowCount() < 8) {
                        MainMemory.addRow(new Object[]{MainMemory.getRowCount(), ""});
                    }
                    break;
                } else {
                    ph = true;
                    hit = true;
                    pageState = "Hit";
                    refrencesList.add(Pagetxt);
                    break;
                }
            }
        }
        if (!ph) {
            pageState = "Fault";
            refrencesList.add(Pagetxt);
            GetData();
            for (int i = 0; i < MainMemory.getRowCount(); i++) {
                MainMemory.setValueAt(Pages[refrencesList.size() - 1][i], i, 1);
            }
            String old, mod = "";
            for (String[] oldMain1 : oldMain) {
                for (int j = 0; j < MainMemory.getRowCount(); j++) {
                    old = oldMain1[1];
                    mod = MainMemory.getValueAt(j, 1).toString();
                    if (old.equals(mod)) {
                        VirtualMemory.setValueAt(j, Integer.parseInt(oldMain1[1]), 1);
                        break;
                    }
                }
            }
            for (int i = 0; i < MainMemory.getRowCount(); i++) {
                if (MainMemory.getValueAt(i, 1).toString().equals(Pagetxt)) {
                    mod = i + "";
                    break;
                }
            }
            for (int i = 0; i < VirtualMemory.getRowCount(); i++) {
                if (VirtualMemory.getValueAt(i, 0).toString().equals(Pagetxt)) {
                    VirtualMemory.setValueAt(mod, i, 1);
                    break;
                }
            }
            boolean g;
            g = false;
            for (String[] oldMain1 : oldMain) {
                old = oldMain1[1];
                g = false;
                for (int j = 0; j < MainMemory.getRowCount(); j++) {
                    if (old.equals(MainMemory.getValueAt(j, 1))) {
                        g = true;
                        break;
                    }
                }
                if (!g) {
                    VirtualMemory.setValueAt("", Integer.parseInt(oldMain1[1]), 1);
                    break;
                }
            }
        } else {
            GetData();
        }
    }

    public void GetData() {
        int i, j=0;
        flg = false;
        boolean fl = false, bl = false;
        pfi = 1;
        phi = 0;
        col = refrencesList.size();
        Pages = new Object[col][row];
        for (i = 0; i < col; i++) {
            for (j = 0; j < row - 1; j++) {
                Pages[i][j] = "";
            }
        }
        for (i = 0; i < col; i++) {
            Pages[i][row - 1] = "PF";
        }
        Pages[0][0] = refrencesList.get(0);
        in=refrencesList.get(0);
        for (i = 1; i < col; i++) {
            flg = false;
            fl = false;
            bl = false;
            for (k = 0; k < i && k < row - 1 && !flg; k++) {
                if (Pages[i - 1][k].toString().equals(refrencesList.get(i))) {
                    if (Pages[i - 1][k].toString().equals("")) {
                        flg = false;
                        fl = false;
                    } else {
                        fl = true;
                        flg = true;
                        Pages[i][row - 1] = "PH";
                        in="-";
                    }
                    
                }
            }
            if (!flg) {
                /*for (k = 0; k < i && k < row - 1; k++) {
                    if (Pages[i - 1][k].equals("") && !refrencesList.get(i).equals("")) {
                        Pages[i][k] = refrencesList.get(i);
                        bl = true;
                        pfi++;
                        for (j = 0; j <= i && j < row - 1; j++) {
                            if (j != k) {
                                Pages[i][j] = Pages[i - 1][j];
                            }
                        }
                        break;
                    }
                }*/
                in=refrencesList.get(i);
                if (!bl || hit2) {
                    insertLast(i, j);
                }
                
            } else {
                for (k = 0; k < i && k < row - 1; k++) {
                    Pages[i][k] = Pages[i - 1][k];
                }
                phi++;
            }
        }
        if(out.equals("")){
            out="-";
        }
    }

    public void insertFirst(int i, int j) {
        for (j = 1; j <= i && j < row - 1; j++) {
            Pages[i][j] = Pages[i - 1][j - 1];
        }
        Pages[i][0] = refrencesList.get(i);
        pfi++;
    }

    @SuppressWarnings("empty-statement")
    public void insertLast(int i, int j) {
        for (j = 0; j < i && j < row - 1; j++) {
            Pages[i][j] = Pages[i - 1][j];
        }
        Pages[i][pfi % numberOfPages] = refrencesList.get(i);
        out=Pages[i-1][pfi % numberOfPages].toString();
        pfi++;
        
    }
    
    public void getOut(){
        boolean exist=false;
        int idx=-1;
        for (Object Page : Pages[Pages.length-2]) {
            exist=false;
            for (Object Page1 : Pages[Pages.length-1]) {
                if (Page.equals(Page1)) {
                    exist=true;
                    break;
                }
            }
            if (!exist) {
                out = Page.toString();
                break;
            }
        }
    }
}
