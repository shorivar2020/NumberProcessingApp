package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NumberHandler {
    private static final Logger LOGGER = Logger.getLogger(NumberHandler.class.getName());
    public void parser(String[] args){
        LOGGER.info("Program started");
        if (args.length < 1 || args.length > 2){
            LOGGER.severe("Invalid number of arguments");
            return;
        }
        List<Integer> numbers = null;
        if (Character.isDigit(args[0].charAt(0)) && Integer.parseInt(args[0]) > 0) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
                numbers = readNumbers(bufferedReader);
            } catch (IOException e) {
                LOGGER.severe("Error reading standard input: " + e);
            }
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]))){
                numbers = readNumbers(bufferedReader);
            } catch (IOException e) {
                LOGGER.severe("Buffer reading file error " + e);
            }
        }
        if (numbers != null) {
            numbers = processNumbers(numbers);
            if (args.length == 2)
                writeToFile(numbers, args[1]);
            else
                printOutput(numbers);
        }
        LOGGER.info("Program finished");
    }
    public List<Integer> readNumbers(BufferedReader reader) throws IOException {
        LOGGER.info("Start read numbers");
        String line;
        ArrayList<Integer> numbers = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            int number = Integer.parseInt(line);
            numbers.add(number);
        }
        return numbers;
    }

    public List<Integer> processNumbers(List<Integer> numbers){
        LOGGER.info("Start process numbers");
        if(numbers.size() % 2 == 0)
            return numbers.stream().filter(number -> number % 2 == 0).toList();
        else
            return numbers.stream().filter(number -> number % 2 != 0).toList();
    }

    public void printOutput(List<Integer> numbers){
        LOGGER.info("Start process numbers");
        numbers.forEach(System.out::println);
    }

    public void writeToFile(List<Integer> numbers, String outputFile) {
        LOGGER.info("Start write to file");
        if (outputFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))){
                for(Integer n: numbers){
                    writer.write(Integer.toString(n));
                    writer.newLine();
                }
            } catch (IOException e) {
                LOGGER.info("Write to file error " + e);
            }
        }
    }
}
