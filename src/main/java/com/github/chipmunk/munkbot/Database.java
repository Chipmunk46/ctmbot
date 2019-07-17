package com.github.chipmunk.munkbot;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class Database {

	private ArrayList<MapData> maps;
	private ArrayList<SeriesData> series;
	private ArrayList<EmbedBuilder> mapEmbedList;
	private ArrayList<EmbedBuilder> seriesEmbedList;
	// Initialize database
	public Database() throws IOException {
		this.maps = new ArrayList<MapData>();
		this.series = new ArrayList<SeriesData>();
		this.mapEmbedList = new ArrayList<EmbedBuilder>();
		this.seriesEmbedList = new ArrayList<EmbedBuilder>();
		// Load in Maps
		loadMaps();
		// Load in Series
		loadSeries();
		// Add maps to series
		addMapsToSeries();
		
		
		writeAllMaps();
		writeAllSeries();
		
		generateMapEmbedList();
		generateSeriesEmbedList();
	}
	public MapData getMap(int index) {
		return maps.get(index);
	}
	
	public SeriesData getSeries(int index) {
		System.out.println("series size " + series.size());
		return series.get(index);	
	}
	
	public EmbedBuilder getMapListPage(int index) {
		return mapEmbedList.get(index);
	}
	
	public EmbedBuilder getSeriesListPage(int index) {
		return seriesEmbedList.get(index);
	}
	
	// Add map to database
	public void addMap(String mapName, String mapDescription, String mapVersion, String mapLength, String mapSeries, String mapImage, String mapDownload) {
		String mapNameU = mapName;
		String mapNameE = mapNameU.replace("_", " ");
		String mapSeriesU = mapSeries;
		String mapSeriesE = mapSeriesU.replace("_", " ");
		MapData map = new MapData(mapNameE,mapDescription,mapVersion,mapLength,mapSeriesE,mapImage,mapDownload);
		maps.add(map);
		writeMapToFile(map);
		System.out.println(mapName + " added.");
	}
	
	public void addMapNoLog(String mapName, String mapDescription, String mapVersion, String mapLength, String mapSeries, String mapImage, String mapDownload) {
		String mapNameU = mapName;
		String mapNameE = mapNameU.replace("_", " ");
		String mapSeriesU = mapSeries;
		String mapSeriesE = mapSeriesU.replace("_", " ");
		MapData map = new MapData(mapNameE,mapDescription,mapVersion,mapLength,mapSeriesE,mapImage,mapDownload);
		maps.add(map);
		System.out.println(mapName + " added.");
	}
	
	// Add series to database
	public void addSeries(String seriesName, String seriesAuthor, String seriesImage, String seriesDescription) {
		String seriesNameU = seriesName;
		String seriesNameE = seriesNameU.replace("_", " ");
		SeriesData seriesobject = new SeriesData(seriesNameE,seriesAuthor, seriesImage, seriesDescription);
		series.add(seriesobject);
		writeSeriesToFile(seriesobject);
		System.out.println(seriesName + " added.");
	}
	
	public void addSeriesNoLog(String seriesName, String seriesAuthor, String seriesImage, String seriesDescription) {
		String seriesNameU = seriesName;
		String seriesNameE = seriesNameU.replace("_", " ");
		SeriesData seriesobject = new SeriesData(seriesNameE,seriesAuthor, seriesImage, seriesDescription);
		series.add(seriesobject);
		System.out.println(seriesName + " added.");
	}
	// Add maps to maplist in each series
	public void addMapsToSeries() {
		// Loop through all series
		for(SeriesData series : series) {
			// For each series, loop through all maps and find which maps have 
			// the same mapSeries value
			for(MapData map : maps) {
				if(map.getMapSeries().equals(series.getSeriesName())) {
					// Add selected map to selected series
					series.addMap(map);
					series.getSeriesEmbed().addInlineField(map.getName(), map.getVersion());
				}
			}
		}
	}
	// Write specific map to file.
	public void writeMapToFile(MapData map) {
		String fileName = "data/mapInfo.txt";
		String mapName = map.getName().replace(" ", "_");
		String mapDescription = map.getDescription();
		String mapVersion = map.getVersion();
		String mapLength = map.getLength();
		String mapSeries = map.getMapSeries().replace(" ", "_");
		String mapImage = map.getMapImage();
		String mapDownload = map.getMapDownload();
		try {
			FileWriter fileWriter = new FileWriter(fileName,true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			
			bufferedWriter.write(mapName);
			bufferedWriter.write("|");
			bufferedWriter.write(mapDescription);
			bufferedWriter.write("|");
			bufferedWriter.write(mapVersion);
			bufferedWriter.write("|");
			bufferedWriter.write(mapLength);
			bufferedWriter.write("|");
			bufferedWriter.write(mapSeries);
			bufferedWriter.write("|");
			bufferedWriter.write(mapImage);
			bufferedWriter.write("|");
			bufferedWriter.write(mapDownload);
			bufferedWriter.newLine();
			
			bufferedWriter.close();
			
			
			
		} catch (IOException ex){
			System.out.println("Error writing to flie '" + fileName + "'");
		}
	}
	
	// Write specific series to file.
	public void writeSeriesToFile(SeriesData series) {
		String fileName = "data/seriesInfo.txt";
		try {
			FileWriter fileWriter = new FileWriter(fileName,true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			String seriesName = series.getSeriesName().replace(" ", "_");
			String seriesAuthor = series.getSeriesAuthor();
			String seriesImage = series.getSeriesImage();
			String seriesDescription = series.getSeriesDescription();
			
			bufferedWriter.write(seriesName);
			bufferedWriter.write("|");
			bufferedWriter.write(seriesAuthor);
			bufferedWriter.write("|");
			bufferedWriter.write(seriesImage);
			bufferedWriter.write("|");
			bufferedWriter.write(seriesDescription);
			bufferedWriter.newLine();
			bufferedWriter.close();
			
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
		}
	}
	
	public int getMapIndexNumber(String name) {
		int i = 0;
		for (MapData map : maps) {
			
			if (map.getName().replace(" ", "_").equals(name)) {
				System.out.println("index: " + i);
				break;
			}
			i++;
			if (i >= (maps.size())) {
				System.out.println("map not found");
			}
		}
		return (i);
	}
	// Edit data for map in file
	public void updateMapInFile(String name) throws IOException {
		System.out.println("actual value: " + getMapIndexNumber("Corona_Trials"));
		System.out.println(name);
		System.out.println(getMapIndexNumber(name));
		MapData map = maps.get(getMapIndexNumber(name));
		String data = map.getName() + "|" + map.getDescription() + "|" + map.getVersion() +
				"|" + map.getLength() + "|" + map.getMapSeries() + "|" + map.getMapImage() + "|" +
				map.getMapDownload(); 
		Path path = Paths.get("data/mapInfo.txt");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		lines.set((getMapIndexNumber(name)), data);
		
		Files.write(path, lines, StandardCharsets.UTF_8);
	}
	
	public void deleteMapInFile(String name) throws IOException {
		int i = 0;
		for (MapData mapCycle : maps) {
			if (mapCycle.getName().replace(" ", "_").equals(name)) {
				System.out.println(i);
				break;
			}
			i++;
		}
		String data = ""; 
		Path path = Paths.get("data/mapInfo.txt");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		lines.remove(i);
		Files.write(path, lines, StandardCharsets.UTF_8);
	}

	public int getSeriesIndexNumber(String name) {
		int i = 0;
		for (SeriesData seriesCycle : series) {
			if (seriesCycle.getSeriesName().replace(" ", "_").equals(name)) {
				break;
			}
			i++;
		}
		System.out.println("i value" + i);
		return (i);
	}
	// Edit data for series in file
	public void updateSeriesInFile(String name) throws IOException {
		int i = 0;
		for (SeriesData seriesCycle : series) {
			if (seriesCycle.getSeriesName().equals(name)) {
				System.out.println(i);
				break;
			}
			i++;
		}
		SeriesData ser = series.get(i);
		String data = ser.getSeriesName() + "|" + ser.getSeriesAuthor() +
				"|" + ser.getSeriesImage() + "|" + ser.getSeriesDescription();
		Path path = Paths.get("data/seriesInfo.txt");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		lines.set(i+1, data);
		Files.write(path, lines, StandardCharsets.UTF_8);
	}
	
	public void deleteSeriesInFile(String name) throws IOException {
		int i = 0;
		for (SeriesData seriesCycle : series) {
			if (seriesCycle.getSeriesName().equals(name)) {
				System.out.println(i);
				break;
			}
			i++;
		}
		String data = "";
		Path path = Paths.get("data/seriesInfo.txt");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		//lines.set(i, data);
		lines.remove(i);
		Files.write(path, lines, StandardCharsets.UTF_8);
	}
	
	public void loadMaps() throws IOException {
		FileReader fileReader = new FileReader("data/mapInfo.txt");
		BufferedReader br = new BufferedReader(fileReader);
		String line;
		while ((line = br.readLine()) != null) {
			String[] tempArgs = line.split("\\|");
			addMapNoLog(tempArgs[0],tempArgs[1],tempArgs[2],tempArgs[3],tempArgs[4],tempArgs[5],tempArgs[6]);
		}
		br.close();
		FileWriter fileWriter = new FileWriter("data/mapInfo.txt");
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write("");
		bufferedWriter.close();
	}
	
	public void loadSeries() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data/seriesInfo.txt"));
		String line;
		while ((line = br.readLine()) != null) {
			String[] tempArgs = line.split("\\|");
			addSeriesNoLog(tempArgs[0],tempArgs[1],tempArgs[2],tempArgs[3]);
		}
		br.close();
		FileWriter fileWriter = new FileWriter("data/seriesInfo.txt");
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write("");
		bufferedWriter.close();
	}
	
	public void writeAllMaps() {
		for (MapData mapCycle: maps) {
			writeMapToFile(mapCycle);
		}
	}
	
	public void writeAllSeries() {
		for (SeriesData seriesCycle: series) {
			writeSeriesToFile(seriesCycle);
		}
	}
	
	public ArrayList<MapData> getMapList() {
		return maps;
	}
	
	public ArrayList<SeriesData> getSeriesList() {
		return series;
	}
	
	public boolean hasSameMap(String arg) {
		for (MapData cycle : maps) {
			if (cycle.getName().equals(arg)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasSameSeries(String arg) {
		for (SeriesData cycle : series) {
			if (cycle.getSeriesName().equals(arg) ) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<EmbedBuilder> callMapList() {
		ArrayList<EmbedBuilder> embedMapList = new ArrayList<EmbedBuilder>();
		for (MapData maps : maps) {
			embedMapList.add(maps.getEmbed());
		}
		return embedMapList;
	}
	
	public void generateMapEmbedList() {
		
		double mapSize = ((maps.size()/10)+1);
		System.out.println("Pages required " + mapSize);
		int index = 0;
		for (int i = 1 ; i <= mapSize; i++) {
			
			EmbedBuilder page = new EmbedBuilder();
			page.setAuthor("Map List - Page " + i);
			page.setThumbnail("http://i.imgur.com/XEmaKVo.png");
			page.setColor(Color.ORANGE);
				for (int x = 1; x <= 10; x++) {
					if (index != (maps.size())) {
					page.addInlineField(maps.get(index).getName(), maps.get(index).getVersion());
					index++;
					}
				}
			mapEmbedList.add(page);
			System.out.println("page " + i + " added");
		}
		
	}
	
	public void generateSeriesEmbedList() {
		
		double seriesSize = ((series.size()/10)+1);
		System.out.println("Pages required " + seriesSize);
		int index = 0;
		for (int i = 1 ; i <= seriesSize; i++) {
			
			EmbedBuilder page = new EmbedBuilder();
			page.setAuthor("Series List - Page " + i);
			page.setThumbnail("http://i.imgur.com/XEmaKVo.png");
			page.setColor(Color.ORANGE);
				for (int x = 1; x <= 10; x++) {
					if (index != (series.size())) {
					page.addInlineField(series.get(index).getSeriesName(), series.get(index).getSeriesAuthor());
					index++;
					}
				}
			seriesEmbedList.add(page);
			System.out.println("page " + i + " added");
		}
		
	}
}
