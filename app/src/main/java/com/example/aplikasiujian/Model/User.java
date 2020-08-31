package com.example.aplikasiujian.Model;

public class User {

    /**
     * status : 200
     * message : Success Login
     * data : {"id_user":"28","username":"Dilo","nis":"2027","token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3RcL2RiX3VqaWFuXC8iLCJhdWQiOiJodHRwOlwvXC9sb2NhbGhvc3RcL2RiX3VqaWFuXC8iLCJpYXQiOjE1OTc3NTk4MzIsImV4cCI6MTU5Nzc1OTg5MiwiZGF0YSI6eyJ1c2VyX2lkIjpudWxsfX0.G013JVWjqZXYnAnYfNcg5YCUl0xTeZlDdX0asxPMXh4"}
     */

    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id_user : 28
         * username : Dilo
         * nis : 2027
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3RcL2RiX3VqaWFuXC8iLCJhdWQiOiJodHRwOlwvXC9sb2NhbGhvc3RcL2RiX3VqaWFuXC8iLCJpYXQiOjE1OTc3NTk4MzIsImV4cCI6MTU5Nzc1OTg5MiwiZGF0YSI6eyJ1c2VyX2lkIjpudWxsfX0.G013JVWjqZXYnAnYfNcg5YCUl0xTeZlDdX0asxPMXh4
         */

        private String id_user;
        private String username;
        private String nis;
        private String token;

        public String getId_user() {
            return id_user;
        }

        public void setId_user(String id_user) {
            this.id_user = id_user;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNis() {
            return nis;
        }

        public void setNis(String nis) {
            this.nis = nis;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
