/*
1. Попросить ввести Фамилию.
2. Открыть файл с названием, как введённая фамилия. Если нет такого файла, то создать его.
3. По очередно попросить ввести имя, отчество, дату рождения, номер телефона, пол.
4. Собрать строковую переменную с введёнными данными.
5. Записать переменную в файл.
6. Закрыть файл.
*/

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("""
                Hello! Let's try to create a card with your personal information.\s
                Please follow the instructions and we'll be fine.\s
                Enter your last name, first name, patronymic, date of birth, phone number, gender.\s
                Rules.\s
                Each parameter is followed by a space.\s
                Date of birth format "dd.mm.yyyy".\s
                The phone number must be a set of numbers without parentheses and hyphens.\s
                Gender mark as "m" (male) or "f" (female).""");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String personalData = reader.readLine();

        String[] personalDatas = personalData.split(" ");

        GeneralVerificaionOfEnteredData(personalDatas);

        StringBuilder result = new StringBuilder();
        for (String data : personalDatas) result.append("<").append(data).append(">");

        String fileName = CreateOfFilesName(personalDatas[0]);

        WriteToFiles(String.valueOf(result), fileName);

    }

    public static boolean CheckingOfNumberOfEnteredData(String[] userDatas) {
        return userDatas.length == 6;
    }

    public static boolean CheckingForThePresenceOfMiddleName() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you have Middle Name? Input 'y' or 'n'.");
        if (sc.hasNext("y")) {
            return false;
        } else return sc.hasNext("n");
    }

    public static boolean CheckingNamesForExtraneousCharacters(String myNames) {
        for (int i = 0; i < myNames.length(); i++) {
            if (Character.isDigit(myNames.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean CheckingOfLengthOfBirthDay(String birthDay) {
        return birthDay.length() == 10;
    }

    public static boolean CheckingOfContentOfBirthDay(String birthDay) {
        return birthDay.charAt(2) == '.' && birthDay.charAt(5) == '.';
    }

    public static boolean CheckingOfPhoneNumber(String phoneNumber) {
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (Character.isDigit(phoneNumber.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean CheckingOfContentOfGender(String gender) {
        return gender.charAt(0) == 'm' || gender.charAt(0) == 'f';
    }

    public static void VerificationOfEnteredDataWithPatronymic(String[] userDatas) throws IOException {
        for (int i = 0; i < userDatas.length; i++) {
            if (i == 0 || i == 1 || i == 2) {
                if (!CheckingNamesForExtraneousCharacters(userDatas[i])) {
                    throw new IOException("Check the entered data. They must be separated by spaces.");
                }
            }
            if (i == 3) {
                if (!CheckingOfLengthOfBirthDay(userDatas[i])) {
                    throw new IOException("Check the entered data against the pattern.");
                } else if (!CheckingOfContentOfBirthDay(userDatas[i])) {
                    throw new IOException("""
                            There are extraneous characters in the date of birth.\s
                            Check the entered data against the pattern.""");
                }
            }
            if (i == 4) {
                if (!CheckingOfPhoneNumber(userDatas[i])) {
                    throw new IOException("""
                            Check the entered data.\s
                            The phone number must not contain any characters other than numbers.""");
                }
            }
            if (i == 5) {
                if (!CheckingOfContentOfGender(userDatas[i])) {
                    throw new IOException("""
                            Check the entered data.\s
                            To indicate the gender, it is enough to write the letter 'm' or 'f'.""");
                }
            }
        }
    }

    public static void VerificationOfEnteredDataWithoutPatronymic(String[] userDatas) throws IOException {
        for (int i = 0; i < userDatas.length; i++) {
            if (i == 0 || i == 1) {
                if (!CheckingNamesForExtraneousCharacters(userDatas[i])) {
                    throw new IOException("Check the entered data. They must be separated by spaces.");
                }
            }
            if (i == 2) {
                if (!CheckingOfLengthOfBirthDay(userDatas[i])) {
                    throw new IOException("Check the entered data against the pattern.");
                } else if (!CheckingOfContentOfBirthDay(userDatas[i])) {
                    throw new IOException("""
                            There are extraneous characters in the date of birth.\s
                            Check the entered data against the pattern.""");
                }
            }
            if (i == 3) {
                if (!CheckingOfPhoneNumber(userDatas[i])) {
                    throw new IOException("""
                            Check the entered data.\s
                            The phone number must not contain any characters other than numbers.""");
                }
            }
            if (i == 4) {
                if (!CheckingOfContentOfGender(userDatas[i])) {
                    throw new IOException("""
                            Check the entered data.\s
                            To indicate the gender, it is enough to write the letter 'm' or 'f'.""");
                }
            }
        }
    }

    public static void GeneralVerificaionOfEnteredData(String[] personalDatas) throws IOException {
        if (!CheckingOfNumberOfEnteredData(personalDatas)) {
            if (CheckingForThePresenceOfMiddleName()) {
                VerificationOfEnteredDataWithoutPatronymic(personalDatas);
            } else {
                throw new IOException("Check the entered data. They must be separated by spaces.");
            }
        } else VerificationOfEnteredDataWithPatronymic(personalDatas);
    }

    public static String CreateOfFilesName(String lastName) {
        String fileName = "";
        return fileName + (lastName + ".txt");
    }

    public static void WriteToFiles(String myData, String fileName) throws IOException {
        Files.write(Paths.get(fileName), myData.getBytes());
    }
}