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

public class LRU {

    boolean flg, hit, isFull;
    ArrayList<String> refrencesList;
    int row, col, pfi, phi, k, tmp, numberOfPages, f;
    Object Pages[][];
    DefaultTableModel MainMemory, VirtualMemory;
    String pageState, in = "", out = "-";
    int[][] matrix, count;

    public LRU(DefaultTableModel list1, DefaultTableModel list2, ArrayList<String> ref, boolean hi) {
        VirtualMemory = list1;
        numberOfPages = 8;
        hit = false;
        matrix = new int[16][16];
        isFull = false;
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
    }

    public void lruProcess(String Pagetxt) {
        boolean ph = false;
        String[][] oldMain = new String[MainMemory.getRowCount()][MainMemory.getColumnCount()];
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
            reArrange(oldMain, Pagetxt);
        } else {
            GetData();
            reArrange(oldMain, Pagetxt);
        }
    }

    private void reArrange(String oldMain[][], String Pagetxt) {
        int min = 20, mink = 0;
        for (int i = 0; i < MainMemory.getRowCount(); i++) {
            MainMemory.setValueAt(Pages[refrencesList.size() - 1][i], i, 1);
            MainMemory.setValueAt(count[refrencesList.size() - 1][i], i, 2);
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
        boolean g = false;
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
    }

    public void GetData() {
        int i, j = 0;
        flg = false;
        boolean fl = false, bl = false;
        pfi = 1;
        phi = 0;
        col = refrencesList.size();
        Pages = new Object[col][row];
        count = new int[col][row];
        for (i = 0; i < col; i++) {
            for (j = 0; j < row; j++) {
                Pages[i][j] = "";
                count[i][j] = 0;
            }
        }
        Pages[0][0] = refrencesList.get(0);
        in = refrencesList.get(0);
        reference(Integer.parseInt(refrencesList.get(0)));
        count[0][0] = getLRU(0, Integer.parseInt(refrencesList.get(0)));
        for (i = 1; i < col; i++) {
            flg = false;
            fl = false;
            bl = false;
            for (k = 0; k < i && k < row && !flg; k++) {
                if (Pages[i - 1][k].toString().equals(refrencesList.get(i))) {
                    if (Pages[i - 1][k].toString().equals("")) {
                        flg = false;
                        fl = false;
                    } else {
                        flg = true;
                        tmp = k;
                        
                    }
                    in = "-";
                }
            }
            
            lrunew(i, j);
        }
        if (i > numberOfPages) {
            getOut();
        }
    }

    public void getOut() {
        boolean exist = false;
        int idx = -1;
        for (Object Page : Pages[Pages.length - 2]) {
            exist = false;
            for (Object Page1 : Pages[Pages.length - 1]) {
                if (Page.equals(Page1)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                out = Page.toString();
                break;
            }
        }
    }

    public void reference(int k) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[k][i] = 1;
            matrix[i][k] = 0;
        }
    }

    public int getLRU(int i, int p) {
        int c = 0;
        for (int n = 0; n < matrix[p].length; n++) {
            if (matrix[p][n] == 1) {
                c++;
            }
        }
        return c;
    }

    public void print(int m[][]) {
        for (int[] m1 : m) {
            for (int j = 0; j < m1.length; j++) {
                System.out.print(m1[j] + "  ");
            }
            System.out.println("");
        }
    }

    public void lrunew(int i, int j) {
        if (!flg) {
            in = refrencesList.get(i);
            int min = 20, mink = 0, c = 0, p, l = 0;
            for (Object page : Pages[i - 1]) {
                if (page.equals("")) {
                    break;
                }
                p = Integer.parseInt(page.toString());
                c = 0;
                for (int n = 0; n < matrix[p].length; n++) {
                    if (matrix[p][n] == 1) {
                        c++;
                    }
                }
                if (min > c) {
                    min = c;
                    mink = p;
                }
                l++;
            }
            for (j = 0; j <= i && j < row; j++) {
                Pages[i][j] = Pages[i - 1][j];
            }
            if (l < numberOfPages) {
                Pages[i][l] = refrencesList.get(i);
                reference(Integer.parseInt(refrencesList.get(i)));
            } else {
                for (int m = 0; m < Pages[i - 1].length; m++) {
                    if (Pages[i - 1][m].equals(mink + "")) {
                        Pages[i][m] = refrencesList.get(i);
                        reference(Integer.parseInt(refrencesList.get(i)));
                        break;
                    }
                }
            }
            /*if (!isFull) {
                for (j = 1; j <= i && j < row - 1; j++) {
                    Pages[i][j] = Pages[i - 1][j - 1];
                }
                Pages[i][0] = refrencesList.get(i);
                if (!Pages[i][7].equals("")) {
                    isFull = true;
                }
            } else {
                int min = 20, mink = 0, c = 0, p;
                for (Object page : Pages[i - 1]) {
                    if (page.equals("")) {
                        break;
                    }
                    p = Integer.parseInt(page.toString());
                    for (int n = 0; n < matrix[p].length; n++) {
                        if (matrix[p][n] == 1) {
                            c++;
                        }
                    }
                    if (min > c) {
                        min = c;
                        mink = p;
                    }
                }
                for (j = 0; j <= i && j < row - 1; j++) {
                    Pages[i][j] = Pages[i - 1][j];
                }
                Pages[i][mink] = refrencesList.get(i);
            }*/
            pfi++;

        } else {
            reference(Integer.parseInt(refrencesList.get(i)));
            for (j = 0; j <= i && j < row; j++) {
                Pages[i][j] = Pages[i - 1][j];
            }
            /*Pages[i][0] = Pages[i - 1][tmp];
            for (k = 1; k <= tmp && k < row - 1; k++) {
                Pages[i][k] = Pages[i - 1][k - 1];
            }
            for (k = tmp + 1; k < i && k < row - 1; k++) {
                Pages[i][k] = Pages[i - 1][k];
            }*/
            phi++;
        }
        for (int m = 0; m < numberOfPages; m++) {
            if (Pages[i][m].equals("")) {
                break;
            }
            count[i][m] = getLRU(i, Integer.parseInt(Pages[i][m].toString()));
        }
        System.out.println("\n\n");
        print(matrix);
    }

    public void insertFirst(int i, int j) {
        if (!flg) {

            for (j = 1; j <= i && j < row; j++) {
                Pages[i][j] = Pages[i - 1][j - 1];
            }
            Pages[i][0] = refrencesList.get(i);
            pfi++;

        } else {
            Pages[i][0] = Pages[i - 1][tmp];
            for (k = 1; k <= tmp && k < row; k++) {
                Pages[i][k] = Pages[i - 1][k - 1];
            }
            for (k = tmp + 1; k < i && k < row; k++) {
                Pages[i][k] = Pages[i - 1][k];
            }
            phi++;
        }

    }

    @SuppressWarnings("empty-statement")
    public void insertLast(int i, int j) {
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
            for (j = 0; j < i && j < row; j++) {
                Pages[i][j] = Pages[i - 1][j];
            }
            Pages[i][pfi % numberOfPages] = refrencesList.get(i);
            pfi++;

        } else {
            int idx = (pfi % numberOfPages);
            if (pfi < 8) {
                for (k = 0; k < tmp && k < row; k++) {
                    Pages[i][k] = Pages[i - 1][k];
                }
                for (k = tmp; k < i && k < row; k++) {
                    Pages[i][k] = Pages[i - 1][k + 1];
                }
                Pages[i][idx - 1] = refrencesList.get(i);
            } else {
                if (tmp > idx + 1) {
                    for (k = idx + 1; k < tmp; k++) {
                        Pages[i][k + 1] = Pages[i - 1][k];
                    }
                    for (k = 0; k < idx + 1; k++) {
                        Pages[i][k] = Pages[i - 1][k];
                    }
                    for (k = tmp + 1; k < row; k++) {
                        Pages[i][k] = Pages[i - 1][k];
                    }
                } else {
                    for (k = idx + 1; k < row; k++) {
                        Pages[i][k + 1] = Pages[i - 1][k];
                    }
                    int l = 7;
                    for (k = 0; k <= tmp; k++) {
                        Pages[i][k] = Pages[i - 1][l % numberOfPages];
                        l++;
                    }
                    for (k = tmp + 1; k < idx + 1; k++) {
                        Pages[i][k] = Pages[i - 1][k];
                    }
                }
                Pages[i][idx + 1] = refrencesList.get(i);
                /*if (idx != -1) {
                    
                } else {
                    idx = numberOfPages - 1;
                    Pages[i][idx] = refrencesList.get(i);
                }*/
            }

            /*for (k = 1; k <= tmp && k < row - 1; k++) {
                Pages[i][k] = Pages[i - 1][k - 1];
            }
            for (k = tmp + 1; k < i && k < row - 1; k++) {
                Pages[i][k] = Pages[i - 1][k];
            }*/
            phi++;
        }

    }
}
