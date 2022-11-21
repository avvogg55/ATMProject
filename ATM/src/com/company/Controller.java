package com.company;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Controller {
    public static void options() throws FileNotFoundException {
        System.out.println("---------ГлЭбБанк---------\nГлэб приветствует вас в приложении ГлЭбБанк\nВыберите опцию введя ее номер\n1.Авторизация" +
                "\n2.Регистрация\n3.Выход");
        Scanner scanner = new Scanner(System.in);
        byte choice = scanner.nextByte();
        switch (choice) {
            case 1:
                loginCardNumber();
                break;
            case 2:
                registration();
                break;
            case 3:
                return;
            default:
                System.out.println("Неверное значение попробуйте еще");
                options();
        }
    }

    public static void loginCardNumber() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("---------ГлЭбБанк---------\n" + "Авторизация\nВедите номер карты в формате XXXX-XXXX-XXXX-XXXX:\n");
        String cardNumber = scanner.next();
        Account account = autorization(Repository.dataBase,cardNumber);
        loginPassword(account,scanner);

    }

    public static void loginPassword(Account account,Scanner scanner) throws  FileNotFoundException {
        System.out.print("Ведите пароль: ");
        String password = scanner.next();
        if(password.equals(account.getPassword())){
            System.out.println("Авторизация пройдена успешно");
            accountOptions(account);
        } else {
            System.out.println("Неверный пароль, попробуйте еще раз");
            loginPassword(account,scanner);
        }
    }

    public static void registration() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("---------ГлЭбБанк---------\n" + "Регистрация\nВедите номер карты\n в формате XXXX-XXXX-XXXX-XXXX: ");
        String cardNumber = scanner.next();
        System.out.print("Ведите пароль: ");
        String password = scanner.next();
        addPerson(cardNumber, password);
    }

    public static void addPerson(String cardNumber,String password){
        try(FileWriter fileWriter = new FileWriter("G:\\JavaProjects\\ATM\\src\\com\\company\\notes3new.txt",true)) {
            fileWriter.write(cardNumber +" "+ password +" 0\n");
            fileWriter.close();
            System.out.print("Данные успешно зарегистрированы");
            options();
        }
        catch(IOException ex){
            ex.getMessage();
        }
    }

    public static Account autorization(File file, String cardNumber) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] words = scanner.nextLine().split(" ");
            if (words[0].equals(cardNumber)) {
                return new Account(words[0], words[1], Integer.parseInt(words[2]));
            }
        }
        System.out.println("Введенного номера карты не существует,\nпроверьте соответствие формата номера карты.");
        options();
        return null;
    }

    public static void accountOptions(Account account) throws FileNotFoundException {
        System.out.println("---------ГлЭбБанк---------\nВы в своей учетной записи\nВыберите что вы хотите сделать введя номер опции\n1.Проверить баланс\n2.Снять деньги со счета\n3.Положить деньги на счет\n4.Выйти из учетной записи");
        Scanner scanner = new Scanner(System.in);
        byte choice = scanner.nextByte();
        switch (choice){
            case 1:
                System.out.println("Ваш баланс составляет - " + account.getBalance() + " р");
                accountOptions(account);
                break;
            case 2:
                reduceMoney(account);
                break;
            case 3:
                addMoney(account);
                break;
            case 4:
                options();
                break;
            default:
                System.out.println("Неверное значение попробуйте еще");
                accountOptions(account);
        }
    }
    public static void reduceMoney(Account account){
        String acc = account.getCardNumber() + " " + account.getPassword() + " " + account.getBalance();
        try(Scanner scanner = new Scanner(Repository.dataBase);FileWriter fileWriter = new FileWriter("G:\\JavaProjects\\ATM\\src\\com\\company\\notes3.txt",false)){
            while (scanner.hasNextLine()){
                String sca = scanner.nextLine();
                if(sca.equals(acc)){
                    continue;
                }
                fileWriter.write(sca + "\n");
            }
            System.out.println("Ведите количество денег которое вы хотите снять: ");
            Scanner scanner1 = new Scanner(System.in);
            int red = scanner1.nextInt();
            if(account.getBalance()>= red) {
                account.setBalance(account.getBalance() - red);
                acc = account.getCardNumber() + " " + account.getPassword() + " " + account.getBalance();
                fileWriter.write(acc + "\n");
                fileWriter.close();
                refresh();
                accountOptions(account);
            } else {
                System.out.println("На счете недостаточно средств.");
                accountOptions(account);
            }

        }
        catch (IIOException e){
            e.getMessage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void refresh(){
        try(Scanner scanner = new Scanner(Repository.databse2);
            FileWriter fileWriter = new FileWriter(Repository.dataBase, false)){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                fileWriter.write(line + "\n");
            }
        }
        catch (IIOException e){
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void addMoney(Account account){
        String acc = account.getCardNumber() + " " + account.getPassword() + " " + account.getBalance();
        try(Scanner scanner = new Scanner(Repository.dataBase);FileWriter fileWriter = new FileWriter("G:\\JavaProjects\\ATM\\src\\com\\company\\notes3.txt",false)){
            while (scanner.hasNextLine()){
                String sca = scanner.nextLine();
                if(sca.equals(acc)){
                    continue;
                }
                fileWriter.write(sca + "\n");
            }
            System.out.println("Ведите количество денег которое вы хотите положить: ");
            Scanner scanner1 = new Scanner(System.in);
            int add = scanner1.nextInt();
            if(add <= 1000000) {
                account.setBalance(account.getBalance() + add);
                acc = account.getCardNumber() + " " + account.getPassword() + " " + account.getBalance();
                fileWriter.write(acc + "\n");
                fileWriter.close();
                refresh();
                accountOptions(account);
            } else {
                System.out.println("Cумма превышает 1000000р.");
                accountOptions(account);
            }

        }
        catch (IIOException e){
            e.getMessage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
