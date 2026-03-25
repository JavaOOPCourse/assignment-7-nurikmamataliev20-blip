import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class  StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        // TODO: реализуйте чтение файла здесь
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\user\\OneDrive\\Desktop\\lab\\assignment-7-nurikmamataliev20-blip\\input\\students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("###")) continue;

                String[] parts = line.split(",");
                if (parts.length != 2) {
                    System.out.println("Invalid format: " + line);
                    continue;
                }

                String name = parts[0].trim();
                try {
                    int score = Integer.parseInt(parts[1].trim());
                    if (score < 0 || score > 100) {
                        throw new InvalidScoreException("Score must be between 0 and 100: " + score);
                    }
                    students.add(new Student(name, score));
                    System.out.println("Valid: " + name + "," + score);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);
                } catch (InvalidScoreException e) {
                    System.out.println("Invalid score: " + e.getMessage() + " (" + name + ")");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: students.txt not found in 'input/' folder.");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь
        if (students.isEmpty()) return;

        double sum = 0;
        highestStudent = students.get(0);

        for (Student student : students) {
            sum += student.getScore();
            if (student.getScore() > highestStudent.getScore()) {
                highestStudent = student;
            }
        }

        averageScore = sum / students.size();

        // Task 8: Sort students by score (descending)
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Integer.compare(s2.getScore(), s1.getScore());
            }
        });
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {


        File outputFile = new File( "report.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.printf("Average: %.1f\n", averageScore);
            if (highestStudent != null) {
                writer.println("Highest: " + highestStudent);
            }
            writer.println("\nFull Sorted List:");
            for (Student student : students) {
                writer.println(student);
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
// class Student (name, score)

class Student{
    private String name;
    private  int  score;
    public Student(String name, int score){
        this.name=name;
        this.score=score;
    }
    public int getScore(){
        return score;
    }
    public String getName(){
        return  name;
    }
    public String toString(){
        return "name: " + name + "\nscore: " + score;
    }

}

class  InvalidScoreException extends Exception{
    public InvalidScoreException(String message){
        super(message);
    }
}
