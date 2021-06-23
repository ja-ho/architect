import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VRManager {
    List<Customer> customers = new ArrayList<Customer>();
    List<Video> videos = new ArrayList<Video>();

    public VRManager() {
    }

    public void init() {
        Customer james = new Customer("James") ;
        Customer brown = new Customer("Brown") ;
        customers.add(james) ;
        customers.add(brown) ;

        Video v1 = new Video("v1", Video.CD, Video.REGULAR, new Date()) ;
        Video v2 = new Video("v2", Video.DVD, Video.NEW_RELEASE, new Date()) ;
        videos.add(v1) ;
        videos.add(v2) ;

        Rental r1 = new Rental(v1) ;
        Rental r2 = new Rental(v2) ;

        james.addRental(r1) ;
        james.addRental(r2) ;
    }

    public void manageReturnVideo(Customer foundCustomer, String videoTitle) {
        List<Rental> customerRentals = foundCustomer.getRentals() ;
        for ( Rental rental: customerRentals ) {
            if (isRentedVideo(videoTitle, rental)) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break ;
            }
        }
    }

    private boolean isRentedVideo(String videoTitle, Rental rental) {
        return rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented();
    }


    void ListCustomerRentals(Customer foundCustomer) {
        System.out.println("Name: " + foundCustomer.getName() +
                "\tRentals: " + foundCustomer.getRentals().size());
        for (Rental rental : foundCustomer.getRentals()) {
            System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
            System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
        }
    }

    Customer getFoundCustomer(String customerName) {
        Customer foundCustomer = null;
        for (Customer customer : customers) {
            if (customer.getName().equals(customerName)) {
                foundCustomer = customer;
                break;
            }
        }
        return foundCustomer;
    }

    Video getFoundVideo(String videoTitle) {
        Video foundVideo = null;
        for ( Video video: videos ) {
            if ( video.getTitle().equals(videoTitle) && video.isRented() == false ) {
                foundVideo = video ;
                break ;
            }
        }
        return foundVideo;
    }
}
