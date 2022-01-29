public class sqlTest {

    public static void main(String[] args) {
        WallMassageDao massageDao = new WallMassageDao();
        System.out.println(massageDao.readWallMassageUserId(11));
    }
}
