
package Main;


public class Password implements Comparable{
        private String Username;
        private String Password;
        private String Site;

        public Password() {
        }

        public Password(String Username, String Password, String Site) {
                this.Username = Username;
                this.Password = Password;
                this.Site = Site;
        }
        
        public Password(String[] info) {
                this.Username = info[0];
                this.Password = info[1];
                this.Site = info[2];
        }

        public String getUsername() {
                return Username;
        }

        public void setUsername(String Username) {
                this.Username = Username;
        }

        public String getPassword() {
                return Password;
        }

        public void setPassword(String Password) {
                this.Password = Password;
        }

        public String getSite() {
                return Site;
        }

        public void setSite(String Site) {
                this.Site = Site;
        }

        @Override
        public String toString() {
                return "Password{" + "Username=" + Username + ", Password=" + Password + ", Site=" + Site + '}';
        }

        @Override
        public int compareTo(Object o) {
                if(o instanceof Password p){
                        return this.getSite().compareTo(p.getSite());
                }
                return -1000;
        }
}
