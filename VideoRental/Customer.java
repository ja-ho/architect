import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String name;
	private double totalCharge;
	private int totalPoint;

	private List<Rental> rentals = new ArrayList<Rental>();

	public Customer(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public void addRental(Rental rental) {
		rentals.add(rental);

	}

	// SRP violation - Long Method (Report Generate & Calculate)
	// Divergent Change
	// Feature Envy
	//
	private void calculateChargePoint() {
		List<Rental> rentals = getRentals();

		this.totalCharge = 0.0;
		this.totalPoint  = 0;

		for (Rental each : rentals) {

			int daysRented = each.calculateDaysRented();
			double eachCharge = each.calculateEachCharge(daysRented);
			int eachPoint = each.calculateEachPoint(daysRented);

			this.totalCharge += eachCharge;
			this.totalPoint += eachPoint ;
		}
	}

	public String getReport() {
		String result = "Customer Report for " + getName() + "\n";

		List<Rental> rentals = getRentals();

		calculateChargePoint();

		for (Rental each : rentals) {
			result += "\t" + each.getVideo().getTitle() + "\tDays rented: " + each.getDaysRented() + "\tCharge: " + each.getCharge()
					+ "\tPoint: " + each.getPoint() + "\n";
		}

		result += "Total charge: " + this.totalCharge + "\tTotal Point:" + this.totalPoint + "\n";

		if ( totalPoint >= 10 ) {
			System.out.println("Congrat! You earned one free coupon");
		}
		if ( totalPoint >= 30 ) {
			System.out.println("Congrat! You earned two free coupon");
		}
		return result ;
	}
	public void clearRentals() {
		rentals.clear();
	}

	void ListCustomerRentals() {
		System.out.println("Name: " + getName() +
				"\tRentals: " + rentals.size());
		for (Rental rental : rentals) {
			System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ");
			System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode());
		}
	}
}
