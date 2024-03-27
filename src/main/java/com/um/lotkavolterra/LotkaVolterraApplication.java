package com.um.lotkavolterra;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LotkaVolterraApplication {

  public static void main(String[] args) {
    SpringApplication.run(LotkaVolterraApplication.class, args);
    if(args.length != 7) {
      System.out.println(
          "Uso: java PredatorPreySimulation <cantLiebres> <cantZorros> <tasaNatalidadLiebres> <tasaMortalidadZorros> <capacidadTerreno> <deltaT> <totalSemanas>");
      System.exit(1);
    }

    // Parsear los argumentos de línea de comandos
    int cantLiebres = Integer.parseInt(args[0]);
    int cantZorros = Integer.parseInt(args[1]);
    double tasaNatalidadLiebres = Double.parseDouble(args[2]);
    double tasaMortalidadZorros = Double.parseDouble(args[3]);
    int capacidadTerreno = Integer.parseInt(args[4]);
    int deltaT = Integer.parseInt(args[5]);
    int totalSemanas = Integer.parseInt(args[6]);

    ArrayList<Integer> historialLiebres = new ArrayList<>();
    ArrayList<Integer> historialZorros = new ArrayList<>();

    System.out.println(
        "LIEBRES: " + cantLiebres + " - ZORROS " + cantZorros + " - TASA NAT. LIEBRES " + tasaNatalidadLiebres + " - TASA MORT. ZORROS " + tasaMortalidadZorros + " - CAPACIDAD TERRENO: " + capacidadTerreno);

    for(int i = 0; i < totalSemanas; i += deltaT) {
      int[] result = step(cantZorros, cantLiebres, tasaNatalidadLiebres, tasaMortalidadZorros, capacidadTerreno, deltaT);
      cantLiebres = result[0];
      cantZorros = result[1];
      historialLiebres.add(cantLiebres);
      historialZorros.add(cantZorros);
    }

    // Mostrar los resultados
    for(int i = 0; i < historialLiebres.size(); i++) {
      System.out.println("Semana " + i + ": Liebres=" + historialLiebres.get(i) + ", Zorros=" + historialZorros.get(i));
    }

    // Crear y mostrar el gráfico
    displayChart(historialLiebres, historialZorros);
  }

  public static int[] step(int cantZorros, int cantLiebres, double tasaNatalidadLiebres, double tasaMortalidadZorros, int capacidadTerreno,
                           int deltaT) {
    int capacidadActual = capacidadTerreno - cantLiebres;

    double incrementoLiebres = (capacidadActual / (double) capacidadTerreno) * (tasaNatalidadLiebres * cantLiebres);
    double disminucionZorros = tasaMortalidadZorros * cantZorros;

    int caza = cantZorros * cantLiebres;

    int nuevaCantLiebres = (int) (cantLiebres + deltaT * (incrementoLiebres - 0.002 * caza));
    int nuevaCantZorros = (int) (cantZorros + deltaT * (0.0004 * caza - disminucionZorros));

    System.out.println(
        "LIEBRES: " + nuevaCantLiebres + " - ZORROS " + nuevaCantZorros + " - TASA NAT. LIEBRES " + tasaNatalidadLiebres + " - TASA MORT. ZORROS " + tasaMortalidadZorros + " - CAPACIDAD TERRENO: " + capacidadActual);

    return new int[]{nuevaCantLiebres, nuevaCantZorros};
  }

  public static void displayChart(List<Integer> historialLiebres, List<Integer> historialZorros) {
    double[] semanas = new double[historialLiebres.size()];
    double[] poblacionLiebres = historialLiebres.stream().mapToDouble(Integer::doubleValue).toArray();
    double[] poblacionZorros = historialZorros.stream().mapToDouble(Integer::doubleValue).toArray();

    for (int i = 0; i < semanas.length; i++) {
      semanas[i] = i;
    }

    XYChart chart = QuickChart.getChart("Evolución de Liebres y Zorros", "Semanas", "Población", "Liebres", semanas, poblacionLiebres);
    chart.addSeries("Zorros", semanas, poblacionZorros);

    new SwingWrapper<>(chart).displayChart();
  }
}