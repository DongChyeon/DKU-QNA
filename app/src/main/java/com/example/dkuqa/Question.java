package com.example.dkuqa;

public class Question {
    public int Qno;
    public String Qtitle;
    public String Qcontent;
    public String Qcategory;

    public int getQno() {
        return Qno;
    }

    public String getQtitle() {
        return Qtitle;
    }

    public String getQcontent() {
        return Qcontent;
    }

    public String getQcategory() {
        return Qcategory;
    }

    public void setQno(int qno) {
        Qno = qno;
    }

    public void setQtitle(String qtitle) {
        Qtitle = qtitle;
    }

    public void setQcontent(String qcontent) {
        Qcontent = qcontent;
    }

    public void setQcategory(String qcategory) {
        Qcategory = qcategory;
    }
}
