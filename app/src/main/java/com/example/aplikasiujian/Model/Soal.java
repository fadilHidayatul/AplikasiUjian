package com.example.aplikasiujian.Model;

import java.util.List;

public class Soal {

    /**
     * success : 1
     * status : 200
     * message : Data didapatkan
     * DATA : [{"id_soal":"13","id_matpel":"3","mata_pelajaran":"matematika","waktu_pengerjaan":"75","image_soal":null,"text_soal":"3 + 1","a":"2","b":"4","c":"6","d":"10","kunci":"4"},{"id_soal":"12","id_matpel":"3","mata_pelajaran":"matematika","waktu_pengerjaan":"75","image_soal":"/ujianTA/soal/img/matematika/home-outline.png","text_soal":"lai pandai ang","a":"lai","b":"kurang pandai","c":"pandai bana","d":"tanyo amak lu","kunci":"lai"}]
     */

    private int success;
    private int status;
    private String message;
    private List<DATABean> DATA;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DATABean> getDATA() {
        return DATA;
    }

    public void setDATA(List<DATABean> DATA) {
        this.DATA = DATA;
    }

    public static class DATABean {
        /**
         * id_soal : 13
         * id_matpel : 3
         * mata_pelajaran : matematika
         * waktu_pengerjaan : 75
         * image_soal : null
         * text_soal : 3 + 1
         * a : 2
         * b : 4
         * c : 6
         * d : 10
         * kunci : 4
         */

        private String id_soal;
        private String id_matpel;
        private String mata_pelajaran;
        private String waktu_pengerjaan;
        private Object image_soal;
        private String text_soal;
        private String a;
        private String b;
        private String c;
        private String d;
        private String kunci;

        public String getId_soal() {
            return id_soal;
        }

        public void setId_soal(String id_soal) {
            this.id_soal = id_soal;
        }

        public String getId_matpel() {
            return id_matpel;
        }

        public void setId_matpel(String id_matpel) {
            this.id_matpel = id_matpel;
        }

        public String getMata_pelajaran() {
            return mata_pelajaran;
        }

        public void setMata_pelajaran(String mata_pelajaran) {
            this.mata_pelajaran = mata_pelajaran;
        }

        public String getWaktu_pengerjaan() {
            return waktu_pengerjaan;
        }

        public void setWaktu_pengerjaan(String waktu_pengerjaan) {
            this.waktu_pengerjaan = waktu_pengerjaan;
        }

        public Object getImage_soal() {
            return image_soal;
        }

        public void setImage_soal(Object image_soal) {
            this.image_soal = image_soal;
        }

        public String getText_soal() {
            return text_soal;
        }

        public void setText_soal(String text_soal) {
            this.text_soal = text_soal;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public String getKunci() {
            return kunci;
        }

        public void setKunci(String kunci) {
            this.kunci = kunci;
        }
    }
}
