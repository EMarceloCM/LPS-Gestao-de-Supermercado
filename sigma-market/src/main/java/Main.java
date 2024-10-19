import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import view.FrMainView;

public class Main {
    public static void main(String[] args) {
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-persistence");
//        EntityManager entityManager = factory.createEntityManager();
//
//        entityManager.close();
//        factory.close();

        FrMainView fr = new FrMainView();
    }
}