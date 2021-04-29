package hu.alkfejl;

import hu.alkfejl.DAO.SimplePlayerDAO;
import hu.alkfejl.model.PlayerModel;

public class Main {

    public static void main(String[] args) {
        SimplePlayerDAO dao = new SimplePlayerDAO();

        System.out.println("Before insert");
        dao.getAll().forEach(System.out::println);

        PlayerModel newPlayer = new PlayerModel();
        newPlayer.setName("physx");
        newPlayer.setScore(0);
        dao.save(newPlayer);

        System.out.println("After insert");
        dao.getAll().forEach(System.out::println);

//        System.out.println("Before delete");
//        dao.getAll().forEach(System.out::println);
//
//        dao.delete("physx");
//
//        System.out.println("After delete");
//        dao.getAll().forEach(System.out::println);
//
//        System.out.println("Delete again");
//        dao.delete("physx");
//        dao.getAll().forEach(System.out::println);
    }
}
