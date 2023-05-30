/**
 * Nama: Hans Naufal Granito
 * NPM: 50421591
 * Kelas: 2IA12
 * Assignment: Membuat sebuah program dengan menggunakan konsep OOP
 * 
 * Program yang dibuat: 
 * Sebuah prototype pada sistem vending machine untuk menjual
 * 5 minuman dengan menggunakan mata uang rupiah.
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 * Any class that implements this interface
 * will have an info function
 * which is used to share the report
 */
interface attr {
    public void info();
}

/**
 * Menu class is used to create a list of item that will
 * available in the vending machine
 */
class Menu implements attr {
    // Variable that stores each items in the array of object
    private VendingMachine[] items = new VendingMachine[5];

    /**
     * List of items for vending machine
     */
    Menu() {
        this.items[0] = new VendingMachine("Milk", 8000);
        this.items[1] = new VendingMachine("Cappuccino Coffee", 8000);
        this.items[2] = new VendingMachine("Mineral Water", 5000);
        this.items[3] = new VendingMachine("Pepsi", 8000);
        this.items[4] = new VendingMachine("Yogurt", 10000);
    }

    /**
     * info function from Menu class shows all the items
     * and their costs in the vending machine
     */
    @Override
    public void info() {
        /* Get the list of item */
        System.out.println("========== Item List ==========");
        for (int i = 0; i < this.items.length; i++) {
            System.out.printf("%d. %s : Rp %,.2f\n", i + 1, this.items[i].get_name(), this.items[i].get_cost());
        }
        System.out.println("===============================");
    }

    /**
     * get the name of the chosen item
     */
    String get_chosen_item_name(int x) {
        return this.items[x - 1].get_name();
    }

    /**
     * get the price of the chosen item
     */
    double get_chosen_item_cost(int x) {
        return this.items[x - 1].get_cost();
    }

    /**
     * returns an array of object containing each item in the vending machine
     */
    VendingMachine[] get_item_object() {
        return this.items;
    }
}

/**
 * This class is used for making an order, check the resource that are still
 * sufficient
 * to make the order or not, and get the selected item.
 */
class MakeOrder implements attr {
    protected Hashtable<String, Integer> resources = new Hashtable<String, Integer>();

    MakeOrder() {
        this.resources.put("Milk", 10);
        this.resources.put("Cappuccino Coffee", 10);
        this.resources.put("Mineral Water", 10);
        this.resources.put("Pepsi", 10);
        this.resources.put("Yogurt", 10);
    }

    /**
     * info method from MakeOrder class
     * Returns an information about item pcs
     */
    @Override
    public void info() {
        System.out.println("==== Vending Machine Stuff ====");
        for (String k : this.resources.keySet()) {
            System.out.println(k + ": " + this.resources.get(k) + " pcs");
        }
    }

    /**
     * Method that will check amount of specific item
     */
    int check_amount(String key, int pcs) {
        return this.resources.get(key);
    }

    /**
     * Check whether the resource is still sufficient or not.
     * parameter 'key' means the key of available Hashtable.
     * parameter 'pcs' means the total of items you want
     */
    boolean is_sufficient(String key, int pcs) {
        /*
         * Check the resources, if it greater than 0, returns true. else, returns false
         */

        // First, check the pcs is greater than the total amount of menu or not
        if (pcs > this.resources.get(key)) {
            return false;
        } else if (pcs < 1) {
            return false;
        } else {
            // Then, check the resource that still sufficient or not
            return this.resources.get(key) > 0 ? true : false;
        }
    }

    /**
     * get_item function from MakeOrder class
     * gives a successful order message and
     * subtracts the value of chosen key from Hashtable,
     * depending on total pcs you have to order.
     * 
     * Need 3 parameters, the first one is the key of Hashtable(name of item you
     * have to order),
     * and the last one is total pcs (total item you have to order)
     */
    void get_item(String item_name, int pcs) throws InterruptedException {
        int x = 0;
        System.out.print("\nProcessing the " + item_name.toLowerCase());
        while (x < 4) {
            Thread.sleep(700);
            System.out.print(" .");
            x++;
        }
        System.out.printf("\nEnjoy your %s... :D\nThanks for order!\n", item_name.toLowerCase());
        // Subtracts values of a specific key
        this.resources.put(item_name, this.resources.get(item_name) - pcs);
    }
}

/**
 * This class is used to store items
 * that created in the class Menu.
 * Subclass from MakeOrder
 */
class VendingMachine extends MakeOrder {
    private String name;
    private double cost;

    VendingMachine(){}

    VendingMachine(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    /**
     * Get the name of object
     */
    String get_name() {
        return this.name;
    }

    /**
     * Get the cost of object
     */
    double get_cost() {
        return this.cost;
    }

    /**
     * info from VendingMachine class
     * returns information about
     * VendingMachine Status (each item is still available or not?)
     */
    @Override
    public void info() {
        int total_stuff = 0;
        for (String k : super.resources.keySet()) {
            total_stuff += super.resources.get(k);
        }
        String msg = total_stuff == 0 ? 
        "The vending machine ran out of stuff :(" 
        :
        "The vending machine is GOOD :)";
        System.out.println("\n**************************************");
        System.out.println(msg);
        System.out.println("total stuff = " + total_stuff);
        System.out.println("**************************************");
    }

    /**
     * To set the stuff value
     */
    void ven_set(String item_name, int pcs) {
        super.resources.put(item_name, super.resources.get(item_name) - pcs);
    }
}

/**
 * MakePayment class handles every payment
 * in the vending machine
 */
class MoneyHandler implements attr {
    private double total_money;

    MoneyHandler() {
        // Default money
        this.total_money = 100000d;
    }

    /**
     * info function from MoneyHandler
     * return the information about money within
     * the vending machine
     */
    @Override
    public void info() {
        System.out.printf("The vending machine money: Rp %,.2f\n", this.total_money);
    }

    /**
     * For make a payment.
     * Returns the total price of your order
     */
    double make_payment(String key, double cost, int pcs) {
        double total = pcs * cost;
        System.out.printf("\nYou order %d pcs of %s\nTotal Price: Rp %,.2f\n", pcs, key.toLowerCase(), total);
        return total;
    }

    /**
     * for proccessing the money
     */
    void proccess_money(double total_price) {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        double input_money = 0, money_stored = 0, change = 0;
        boolean is_enough = true;

        while (is_enough) {
            System.out.print("\nInsert money : ");
            try{
                input_money = Double.parseDouble(inp.readLine());
                // Store all entered money to money_stored variable
                money_stored += input_money;
    
                // Store all entered money to total_money field
                this.total_money += input_money;
                System.out.printf("Money stored: Rp %,.2f\n", money_stored);
                if (money_stored < total_price) {
                    double minus = total_price - money_stored;
                    System.out.printf("Minus Rp %,.2f\n", minus);
                } else {
                    //hansnaufalgranito-50421591
                    change = (money_stored - total_price);
                    this.total_money -= (money_stored - total_price);
    
                    System.out.printf("Change : Rp %,.2f\n", change);
                    is_enough = false;
                }
            } catch(NumberFormatException e){
                System.out.println("Input number only!!!");
            } catch(Exception e){
                System.out.println(e);
            }
        }
    }
}

public class App {
    public static void main(String[] args) throws InterruptedException {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        VendingMachine ven = new VendingMachine();
        MakeOrder order = new MakeOrder();
        Menu menu = new Menu();
        MoneyHandler money = new MoneyHandler();
        
        boolean res = true;
        boolean start = true;
        String item_name = null;
        Double item_cost = 0d;

        // For user input
        int usr_inp = -1;

        while (start) {
            System.out.println();

            // get the total of menu
            int total_menu = menu.get_item_object().length;

            // Get the list menu
            menu.info();

            // check input
            System.out.print("\nWhat would you like? Input the number: ");
            try {
                usr_inp = Integer.parseInt(inp.readLine());
                // check the total available menu
                if (usr_inp == 0 || usr_inp > total_menu || usr_inp < -2) {
                    System.out.printf("Please choose 1-%d only!!!\n", total_menu);
                } else {
                    /**
                     * Magic keyword
                     * -1 for return a report
                     * -2 for turn off the machine
                     */
                    if (usr_inp == -1) {
                        ven.info();
                        order.info();
                        money.info();
                    } else if (usr_inp == -2) {
                        System.out.println("\n\nTURN OFF\n\n");
                        return;
                    } else {
                        // for input pcs
                        int pcs = 0;
    
                        // get the amount of ordered item
                        boolean is_num = true;
                        while(is_num){
                            System.out.print("\nHow many item? (input number): ");
                            try {
                                pcs = Integer.parseInt(inp.readLine());
                                // Checking the resource
                                item_name = menu.get_chosen_item_name(usr_inp);
                                item_cost = menu.get_chosen_item_cost(usr_inp);
                                res = order.is_sufficient(item_name, pcs);
                                int check_amount = order.check_amount(item_name, pcs);
        
                                if (pcs < 1) {
                                    System.out.println("input > 0!!!");
                                } else if (pcs > check_amount) {
                                    System.out.printf("Sorry, there is %d %s available\n", check_amount, item_name.toLowerCase());
                                }

                                // Stop the is_num loop
                                is_num = false;

                            } catch (NumberFormatException e) {
                                System.out.println("Please input number only!!!");
                            } catch(Exception e){
                                System.out.println(e);
                            }
                        }
    
                        if (res) {
                            // Check Price
                            Double total_price = money.make_payment(item_name, item_cost, pcs);
    
                            // Insert money
                            money.proccess_money(total_price);
    
                            // Get the item
                            order.get_item(item_name, pcs);
                            
                            // Set the machine
                            ven.ven_set(item_name, pcs);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.printf("\nPlease input number (1-%d)!!!\n", total_menu);
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }
}