package com.um.lotkavolterra;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
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
    showPhaseDiagram(historialLiebres, historialZorros);

    // Crea el diagrama poblacional
    showPoblationalDiagram(historialLiebres, historialZorros, totalSemanas, deltaT);
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

  public static void showPhaseDiagram(List<Integer> liebres, List<Integer> zorros) {
    double[] liebresArray = liebres.stream().mapToDouble(Integer::doubleValue).toArray();
    double[] zorrosArray = zorros.stream().mapToDouble(Integer::doubleValue).toArray();

    XYChart chart = QuickChart.getChart("Diagrama de Fase", "Población de liebres", "Población de zorros", "Liebres vs Zorros", liebresArray,
                                        zorrosArray);

    chart.getStyler().setYAxisDecimalPattern("#");

    try {
      BitmapEncoder.saveBitmap(chart, "phase_diagram", BitmapEncoder.BitmapFormat.PNG);
      System.out.println("Diagrama de fase guardado como imagen: phase_diagram.png");
    } catch(IOException e) {
      System.err.println("Error al guardar el diagrama de fase como imagen: " + e.getMessage());
    }
  }


  public static void showPoblationalDiagram(List<Integer> liebres, List<Integer> zorros, int totalSemanas, int deltaT) {
    double[] semanasX = new double[totalSemanas / deltaT];
    for(int i = 0; i < semanasX.length; i++) {
      semanasX[i] = i * deltaT;
    }

    double[] liebresArray = liebres.stream().mapToDouble(Integer::doubleValue).toArray();
    double[] zorrosArray = zorros.stream().mapToDouble(Integer::doubleValue).toArray();

    XYChart chart = QuickChart.getChart("poblacional_diagram", "Tiempo en semanas", "Población", "Liebres", semanasX, liebresArray);
    chart.addSeries("Zorros", semanasX, zorrosArray);

    chart.getStyler().setYAxisDecimalPattern("#");

    try {
      BitmapEncoder.saveBitmap(chart, "diagrama_de_fase", BitmapEncoder.BitmapFormat.PNG);
      System.out.println("Diagrama poblacional guardado como imagen: diagrama_de_fase.png");
    } catch(IOException e) {
      System.err.println("Error al guardar el diagrama poblacional como imagen: " + e.getMessage());
    }
  }
}
