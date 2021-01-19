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

public class SecondChance {

    boolean flg, hit, hit2, isFull;
    ArrayList<String> refrencesList;
    int row, col, pfi, phi, k, numberOfPages, f, victim = -1;
    Object Pages[][], Pages2[][];
    DefaultTableModel MainMemory, VirtualMemory;
    String pageState, refBit[][], ref2[][], in = "", out = "-";

    public SecondChance(DefaultTableModel list1, DefaultTableModel list2, ArrayList<String> ref, boolean hi) {
        VirtualMemory = list1;
        numberOfPages = 8;
        hit = false;
        isFull = false;
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

    public void GetData() {
        int i, j = 0;
        flg = false;
        boolean fl = false, bl = false;
        pfi = 1;
        phi = 0;
        col = refrencesList.size();
        Pages = new Object[col][row];
        Pages2 = new Object[col][row];
        refBit = new String[col][row];
        ref2 = new String[col][row];
        for (i = 0; i < col; i++) {
            for (j = 0; j < row - 1; j++) {
                Pages[i][j] = "";
                refBit[i][j] = "";
                Pages2[i][j] = "";
                ref2[i][j] = "";
            }
        }
        for (i = 0; i < col; i++) {
            Pages[i][row - 1] = "PF";
        }
        Pages[0][0] = refrencesList.get(0);
        refBit[0][0] = "1";
        Pages2[0][0] = refrencesList.get(0);
        in = refrencesList.get(0);
        ref2[0][0] = "1";
        for (i = 1; i < col; i++) {
            flg = false;
            fl = false;
            bl = false;
            for (k = 0; k < i && k < row - 1 && !flg; k++) {
                if (Pages2[i - 1][k].toString().equals(refrencesList.get(i))) {
                    if (Pages2[i - 1][k].toString().equals("")) {
                        flg = false;
                        fl = false;
                    } else {
                        fl = true;
                        flg = true;
                        Pages2[i][row - 1] = "PH";
                    }
                    in = "-";
                }
            }
            if (!flg) {
                /*for (k = 0; k < i && k < row - 1; k++) {
                    if (Pages[i - 1][k].equals("") && !refrencesList.get(i).equals("")) {
                        Pages[i][k] = refrencesList.get(i);
                        bl = true;
                        for (j = 0; j <= i && j < row - 1; j++) {
                            if (j != k) {
                                Pages[i][j] = Pages[i - 1][j];
                            }
                        }
                        break;
                    }
                }*/
                //if (!bl || hit2) {
                int vic = 1;
                in = refrencesList.get(i);
                //Pages2[i][0] = refrencesList.get(i);
                //ref2[i][0] = "1";
                if (Pages2[i - 1][7].equals("")) {
                    /*Pages2[i][0] = refrencesList.get(i);
                    ref2[i][0] = "1";
                    for (j = 7; j >= 1; j--) {
                        Pages2[i][j] = Pages2[i - 1][j - 1];
                        ref2[i][j] = ref2[i - 1][j - 1];
                    }*/

                    for (j = 0; j < i; j++) {
                        Pages2[i][j] = Pages2[i - 1][j];
                        ref2[i][j] = ref2[i - 1][j];
                    }
                    j=0;
                    while(!Pages2[i-1][j].equals("")){
                        j++;
                    }
                    Pages2[i][j] = refrencesList.get(i);
                    ref2[i][j] = "1";
                } else {
                    if (allIsOne(i)) {
                        for (int h = 0; h < 7; h++) {
                            ref2[i][h] = "0";
                        }
                        for (j = 0; j < 7; j++) {
                            Pages2[i][j] = Pages2[i - 1][j + 1];
                        }
                        Pages2[i][7] = refrencesList.get(i);
                        ref2[i][7] = "1";
                    } else {
                        if (ref2[i - 1][0].equals("1")) {
                            //resetPages(i);
                            String page = Pages2[i - 1][0].toString();

                            for (int g = 0; g < 7; g++) {
                                Pages2[i][g] = Pages2[i - 1][g + 1];
                                ref2[i][g] = ref2[i - 1][g + 1];
                            }
                            ref2[i][7] = "0";
                            Pages2[i][7] = page;
                            while (ref2[i][0].equals("1")) {
                                page = Pages2[i][0].toString();
                                for (int g = 0; g <7; g++) {
                                    Pages2[i][g] = Pages2[i][g + 1];
                                    ref2[i][g] = ref2[i][g + 1];
                                }
                                ref2[i][7] = "0";
                                Pages2[i][7] = page;
                            }
                            for(int g=0;g<7;g++){
                                Pages2[i][g]=Pages2[i][g+1];
                                ref2[i][g]=ref2[i][g+1];
                            }
                            ref2[i][7] = "1";
                            Pages2[i][7] = refrencesList.get(i);
                        } else {
                            for (j = 0; j < numberOfPages; j++) {
                                Pages2[i][j] = Pages2[i - 1][j + 1];
                                ref2[i][j] = ref2[i - 1][j + 1];
                            }
                            ref2[i][7] = "1";
                            Pages2[i][7] = refrencesList.get(i);
                        }
                    }
                }
                //insertFirst2(i, j);
                //insertLast3(i, j);
                //}
                pfi++;
            } else {
                for (k = 0; k < i && k < 8; k++) {
                    /*Pages[i][k] = Pages[i - 1][k];
                    refBit[i][k] = refBit[i - 1][k];
                    if (Pages[i - 1][k].toString().equals(refrencesList.get(i))) {
                        refBit[i][k] = "1";
                    }*/
                    Pages2[i][k] = Pages2[i - 1][k];
                    ref2[i][k] = ref2[i - 1][k];
                    if (Pages2[i - 1][k].toString().equals(refrencesList.get(i))) {
                        ref2[i][k] = "1";
                    }
                }
                phi++;
            }
        }
        if (!Pages[refrencesList.size() - 1][7].equals("")) {
            isFull = true;
        }
        if (i > numberOfPages) {
            getOut();
        }
        //System.out.println(pfi%numberOfPages);
        //print();
    }

    public void insertLast3(int i, int j) {
        if (Pages[i - 1][7].equals("")) {
            for (j = 0; !Pages[i - 1][j].equals(""); j++) {
                Pages[i][j] = Pages[i - 1][j];
                refBit[i][j] = refBit[i - 1][j];
            }
            Pages[i][j] = refrencesList.get(i);
            refBit[i][j] = "1";
        } else {
            int m;
            boolean Found = false;
            for (m = 0; m < numberOfPages; m++) {
                Found = false;
                for (int y = 0; y < numberOfPages; y++) {
                    if (Pages2[i - 1][m].equals(Pages2[i][y])) {
                        Found = true;
                        break;
                    }
                }
                if (!Found) {
                    break;
                }
            }
            for (int y = 0; y < numberOfPages; y++) {
                if (Pages[i - 1][y].equals(Pages2[i - 1][m])) {
                    m = y;
                    break;
                }
            }
            for (j = 0; j < numberOfPages; j++) {
                Pages[i][j] = Pages[i - 1][j];
            }
            Pages[i][m] = refrencesList.get(i);
            for (int x = 0; x < numberOfPages; x++) {
                for (int y = 0; y < numberOfPages; y++) {
                    if (Pages[i][x].equals(Pages2[i][y])) {
                        refBit[i][x] = ref2[i][y];
                        break;
                    }
                }
            }
            ////////////////////////////////////
            int s;
            if (ref2[i - 1][7].equals("1")) {
                for (s = 0; s < 8; s++) {
                    if (Pages[i][0].equals(Pages2[i][0])) {
                        break;
                    }
                }
                String pg = Pages[i][s].toString();
                for (int d = s; d > 0; d--) {
                    Pages[i][d] = Pages[i][d - 1];
                    refBit[i][d] = refBit[i][d - 1];
                }
                Pages[i][0] = pg;
                refBit[i][0] = refBit[i][s];
                while (ref2[i][7].equals("1")) {
                    ref2[i][0] = "0";
                    Pages2[i][0] = Pages2[i - 1][7];
                    for (int g = 1; g < 8; g++) {
                        Pages2[i][g] = Pages2[i - 1][g - 1];
                        ref2[i][g] = ref2[i - 1][g - 1];
                    }
                }
                ref2[i][7] = "1";
                Pages2[i][7] = refrencesList.get(i);
            }
        }
    }

    public void fifoProcess(String Pagetxt) {
        boolean ph = false;
        String[][] oldMain = new String[MainMemory.getRowCount()][MainMemory.getColumnCount() - 1];
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
                        MainMemory.addRow(new Object[]{MainMemory.getRowCount(), Pagetxt, "0"});
                    }
                    break;
                } else {
                    ph = true;
                    hit = true;
                    for (int i = 0; i < MainMemory.getRowCount(); i++) {
                        if (Pagetxt.equals(MainMemory.getValueAt(i, 1))) {
                            MainMemory.setValueAt("1", i, 2);
                            break;
                        }
                    }
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
            for (int itr = 0; itr < MainMemory.getRowCount(); itr++) {
                MainMemory.setValueAt(Pages2[refrencesList.size() - 1][itr], itr, 1);
                MainMemory.setValueAt(ref2[refrencesList.size() - 1][itr], itr, 2);
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

        } else {
            GetData();
        }
        for (int ki = 0; ki < Pages2[refrencesList.size() - 1].length; ki++) {
            System.out.print(Pages2[refrencesList.size() - 1][ki] + "(" + ref2[refrencesList.size() - 1][ki] + ")  ,");
        }
        System.out.print("\n\n\n");
    }

    public void GetData2() {
        int i, j = 0;
        flg = false;
        boolean fl = false, bl = false;
        pfi = 1;
        phi = 0;
        col = refrencesList.size();
        Pages = new Object[col][row];
        Pages2 = new Object[col][row];
        refBit = new String[col][row];
        ref2 = new String[col][row];
        for (i = 0; i < col; i++) {
            for (j = 0; j < row - 1; j++) {
                Pages[i][j] = "";
                refBit[i][j] = "";
                Pages2[i][j] = "";
                ref2[i][j] = "";
            }
        }
        for (i = 0; i < col; i++) {
            Pages[i][row - 1] = "PF";
        }
        Pages[0][0] = refrencesList.get(0);
        refBit[0][0] = "0";
        Pages2[0][0] = refrencesList.get(0);
        in = refrencesList.get(0);
        ref2[0][0] = "0";
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
                    }
                    in = "-";
                }
            }
            if (!flg) {
                /*for (k = 0; k < i && k < row - 1; k++) {
                    if (Pages[i - 1][k].equals("") && !refrencesList.get(i).equals("")) {
                        Pages[i][k] = refrencesList.get(i);
                        bl = true;
                        for (j = 0; j <= i && j < row - 1; j++) {
                            if (j != k) {
                                Pages[i][j] = Pages[i - 1][j];
                            }
                        }
                        break;
                    }
                }*/
                //if (!bl || hit2) {
                insertFirst2(i, j);
                insertLast2(i, j);
                //}
                pfi++;
            } else {
                for (k = 0; k < i && k < 8; k++) {
                    Pages2[i][k] = Pages2[i - 1][k];
                    ref2[i][k] = ref2[i - 1][k];
                    if (Pages2[i - 1][k].toString().equals(refrencesList.get(i))) {
                        ref2[i][k] = "1";
                    }
                }
                phi++;
            }
        }
        if (!Pages[refrencesList.size() - 1][7].equals("")) {
            isFull = true;
        }
        if (i > numberOfPages) {
            getOut();
        }
        //System.out.println(pfi%numberOfPages);
        //print();
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

    //pages2
    public void insertFirst2(int i, int j) {
        int vic = 1;
        in = refrencesList.get(i);
        Pages2[i][0] = refrencesList.get(i);
        ref2[i][0] = "0";
        if (Pages2[i - 1][7].equals("")) {
            for (j = 7; j >= 1; j--) {
                Pages2[i][j] = Pages2[i - 1][j - 1];
                ref2[i][j] = ref2[i - 1][j - 1];
            }
        } else {
            if (allIsOne(i)) {
                for (int h = 1; h < 8; h++) {
                    ref2[i][h] = "0";
                }
                for (j = 7; j >= 1; j--) {
                    Pages2[i][j] = Pages2[i - 1][j - 1];
                }
            } else {
                if (ref2[i - 1][7].equals("1")) {
                    resetPages(i);
                } else {
                    for (j = 7; j >= 1; j--) {
                        Pages2[i][j] = Pages2[i - 1][j - 1];
                        ref2[i][j] = ref2[i - 1][j - 1];
                    }
                }
            }
        }
    }

    public void insertFirst(int i, int j) {
        int vic = 1;

        Pages[i][0] = refrencesList.get(i);
        refBit[i][0] = "0";
        if (Pages[i - 1][7].equals("")) {
            for (j = 7; j >= 1; j--) {
                Pages[i][j] = Pages[i - 1][j - 1];
                refBit[i][j] = refBit[i - 1][j - 1];
            }
        } else {
            if (allIsOne(i)) {
                for (int h = 1; h < 8; h++) {
                    refBit[i][h] = "0";
                }
                for (j = 7; j >= 1; j--) {
                    Pages[i][j] = Pages[i - 1][j - 1];
                }
            } else {
                if (refBit[i - 1][7].equals("1")) {
                    resetPages(i);
                } else {
                    for (j = 7; j >= 1; j--) {
                        Pages[i][j] = Pages[i - 1][j - 1];
                        refBit[i][j] = refBit[i - 1][j - 1];
                    }
                }
            }
        }
    }

    public void insertLast2(int i, int j) {

        if (Pages[i - 1][7].equals("")) {
            for (j = 0; !Pages[i - 1][j].equals(""); j++) {
                Pages[i][j] = Pages[i - 1][j];
                refBit[i][j] = refBit[i - 1][j];
            }
            Pages[i][j] = refrencesList.get(i);
            refBit[i][j] = "1";
        } else {
            if (allIsOne(i)) {
                for (int h = 0; h < 7; h++) {
                    refBit[i][h] = "0";
                    Pages[i][h] = Pages[i - 1][h];
                }
                Pages[i][(pfi % numberOfPages)] = refrencesList.get(i);
                refBit[i][(pfi % numberOfPages)] = "0";
            } else {
                int m;
                boolean Found = false;
                for (m = 0; m < numberOfPages; m++) {
                    Found = false;
                    for (int y = 0; y < numberOfPages; y++) {
                        if (Pages2[i - 1][m].equals(Pages2[i][y])) {
                            Found = true;
                            break;
                        }
                    }
                    if (!Found) {
                        break;
                    }
                }
                for (int y = 0; y < numberOfPages; y++) {
                    if (Pages[i - 1][y].equals(Pages2[i - 1][m])) {
                        m = y;
                        break;
                    }
                }
                for (j = 0; j < numberOfPages; j++) {
                    Pages[i][j] = Pages[i - 1][j];
                }
                Pages[i][m] = refrencesList.get(i);
                for (int x = 0; x < numberOfPages; x++) {
                    for (int y = 0; y < numberOfPages; y++) {
                        if (Pages[i][x].equals(Pages2[i][y])) {
                            refBit[i][x] = ref2[i][y];
                            break;
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("empty-statement")
    public void insertLast(int i, int j) {
        String old[][], now[][];
        if (!isFull) {
            for (j = 0; !Pages[i - 1][j].equals(""); j++) {
                Pages[i][j] = Pages[i - 1][j];
                refBit[i][j] = refBit[i - 1][j];
            }
            Pages[i][j] = refrencesList.get(i);
            refBit[i][j] = "0";
            if (j == 7) {
                isFull = true;
            }
        } else {
            if (allIsOne(i)) {
                for (int h = 0; h < 7; h++) {
                    refBit[i][h] = "0";
                    Pages[i][h] = Pages[i - 1][h];
                }
                Pages[i][(pfi % numberOfPages)] = refrencesList.get(i);
                refBit[i][(pfi % numberOfPages)] = "0";
            } else {
                boolean found = false;
                int co;
                if (refBit[i - 1][pfi % numberOfPages].equals("1")) {
                    for (co = (pfi % numberOfPages) - 1; co >= 0; co--) {
                        if (refBit[i - 1][co].equals("0")) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        System.arraycopy(Pages[i - 1], 0, Pages[i], 0, numberOfPages);
                        Pages[i][co] = refrencesList.get(i);
                    } else {
                        for (co = 7; co >= pfi % numberOfPages; co--) {
                            if (refBit[i - 1][co].equals("0")) {
                                found = true;
                                break;
                            }
                            System.arraycopy(Pages[i - 1], 0, Pages[i], 0, numberOfPages);
                            Pages[i][co] = refrencesList.get(i);
                        }
                    }
                }
                old = convert(Pages[i - 1], refBit[i - 1], pfi % numberOfPages);
                //if (refBit[i - 1][pfi % numberOfPages].equals("1")) {
                now = resetPages(i, old);
                fillPages(now, i);
                /*} else {
                    for (j = 7; j >= 0; j--) {
                        Pages[i][j] = Pages[i - 1][j];
                        refBit[i][j] = refBit[i - 1][j];
                    }
                    Pages[i][pfi % numberOfPages] = refrencesList.get(i);
                    refBit[i][pfi % numberOfPages] = "0";
                }*/
            }
        }
    }

    private String[][] convert(Object pages[], String refs[], int last) {
        String b[][] = new String[8][2];
        int temp = last - 1;
        for (int i = 0; i < last; i++) {
            b[i][0] = pages[temp].toString();
            b[i][1] = refs[temp];
            temp--;
        }
        temp = last;
        for (int i = 7; i >= last; i--) {
            b[i][0] = pages[temp].toString();
            b[i][1] = refs[temp];
            temp++;
        }
        return b;
    }

    private void fillPages(String a[][], int i) {
        int c = numberOfPages - 1;
        for (int j = (pfi % numberOfPages); j < numberOfPages; j++) {
            Pages[i][j] = a[c][0];
            refBit[i][j] = a[c][1];
            c--;
        }
        c = 0;
        for (int j = (pfi % numberOfPages) - 1; j >= 0; j--) {
            Pages[i][j] = a[c][0];
            refBit[i][j] = a[c][1];
            c++;
        }
    }

    private boolean allIsOne(int k) {
        boolean allisone = true;
        for (int i = 0; i < 8; i++) {
            if (ref2[k - 1][i].equals("0")) {
                allisone = false;
                break;
            }
        }
        return allisone;
    }

    private void resetPages(int i) {
        int it;
        for (it = 7; it >= 1; it--) {
            if (ref2[i - 1][it].equals("0")) {
                break;
            }
        }
        Pages2[i][7] = Pages2[i - 1][7];
        ref2[i][7] = "0";
        for (int it3 = 6; it3 > it; it3--) {
            Pages2[i][it3] = Pages2[i - 1][it3];
            ref2[i][it3] = ref2[i - 1][it3];
        }
        for (int it2 = it; it2 >= 1; it2--) {
            Pages2[i][it2] = Pages2[i - 1][it2 - 1];
            ref2[i][it2] = ref2[i - 1][it2 - 1];
        }
    }

    private String[][] resetPages(int i, String old[][]) {
        String now[][] = new String[8][2];
        int it;
        if (old[7][1].equals("1")) {
            for (it = 6; it >= 1; it--) {
                if (old[it][1].equals("0")) {
                    break;
                }
            }
            now[it][0] = refrencesList.get(i);
            now[it][1] = "0";
            now[7][0] = old[7][0];
            now[7][1] = "0";
            for (int it3 = 6; it3 > it; it3--) {
                now[it3][0] = old[it3][0];
                now[it3][1] = old[it3][1];
            }
            for (int it2 = it - 1; it2 >= 0; it2--) {
                now[it2][0] = old[it2][0];
                now[it2][1] = old[it2][1];
            }
            victim = pfi % numberOfPages;
        } else {
            now[7][0] = refrencesList.get(i);
            now[7][1] = "0";
            for (int it2 = 6; it2 >= 0; it2--) {
                now[it2][0] = old[it2][0];
                now[it2][1] = old[it2][1];
            }
            victim = -1;
        }

        return now;
    }

    public void print() {
        for (int ki = 0; ki < Pages2.length; ki++) {
            {
                for (int t = 0; t < Pages2[ki].length - 1; t++) {
                    System.out.print(Pages2[ki][t].toString() + "[" + ref2[ki][t] + "] - ");
                }
                System.out.println();
            }
        }
        System.out.println("------------\n");
    }
}
