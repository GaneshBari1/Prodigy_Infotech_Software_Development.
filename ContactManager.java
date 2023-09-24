import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nPhone Number: " + phoneNumber + "\nEmail: " + email + "\n";
    }
}

public class ContactManager {
    private List<Contact> contacts = new ArrayList<>();
    private static final String CONTACTS_FILE = "contacts.txt";

    public static void main(String[] args) {
        ContactManager contactManager = new ContactManager();
        contactManager.loadContacts();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Contact Management System");
            System.out.println("1. Add Contact");
            System.out.println("2. Edit Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. View Contacts");
            System.out.println("5. Save and Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    contactManager.addContact(scanner);
                    break;
                case 2:
                    contactManager.editContact(scanner);
                    break;
                case 3:
                    contactManager.deleteContact(scanner);
                    break;
                case 4:
                    contactManager.viewContacts();
                    break;
                case 5:
                    contactManager.saveContacts();
                    System.out.println("Contacts saved. Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void addContact(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email address: ");
        String email = scanner.nextLine();

        Contact contact = new Contact(name, phoneNumber, email);
        contacts.add(contact);
        System.out.println("Contact added successfully.");
    }

    public void editContact(Scanner scanner) {
        System.out.print("Enter the name of the contact to edit: ");
        String searchName = scanner.nextLine();

        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(searchName)) {
                System.out.print("Enter new phone number: ");
                String newPhoneNumber = scanner.nextLine();
                System.out.print("Enter new email address: ");
                String newEmail = scanner.nextLine();

                contact = new Contact(contact.getName(), newPhoneNumber, newEmail);
                System.out.println("Contact updated successfully.");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    public void deleteContact(Scanner scanner) {
        System.out.print("Enter the name of the contact to delete: ");
        String searchName = scanner.nextLine();

        Contact contactToRemove = null;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(searchName)) {
                contactToRemove = contact;
                break;
            }
        }

        if (contactToRemove != null) {
            contacts.remove(contactToRemove);
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    public void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONTACTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String phoneNumber = parts[1];
                    String email = parts[2];
                    contacts.add(new Contact(name, phoneNumber, email));
                }
            }
        } catch (IOException e) {
            // File may not exist, or there's an issue with reading it.
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }

    public void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONTACTS_FILE))) {
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }
}
