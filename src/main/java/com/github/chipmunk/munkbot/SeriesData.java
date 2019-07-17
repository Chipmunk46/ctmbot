package com.github.chipmunk.munkbot;

import java.awt.Color;
import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class SeriesData {
	private String seriesName;
	private String seriesAuthor;
	private String seriesImage;
	private String seriesDescrption;
	private EmbedBuilder seriesEmbed;
	private ArrayList<MapData> maps;
	
	public SeriesData(String seriesName, String seriesAuthor, String seriesImage, String seriesDescription) {
		this.seriesName = seriesName;
		this.seriesAuthor = seriesAuthor;
		this.maps = new ArrayList<MapData>();
		this.seriesImage = seriesImage;
		this.seriesDescrption = seriesDescription;
		this.seriesEmbed = new EmbedBuilder()
				.setTitle(seriesName)
			    .setDescription(seriesDescription)
			    .setAuthor(seriesAuthor)
			    .setColor(Color.ORANGE)
			    .setFooter("Link to series here!")
			    .setThumbnail("http://i.imgur.com/XEmaKVo.png");
		if (this.seriesImage.startsWith("http")) {
			this.seriesEmbed.setImage(this.seriesImage);
		}
	}

	
	public void addMap(MapData map) {
		maps.add(map);
	}
	
	public MapData getMapIndex(int index) {
		return maps.get(index);
	}
	
	public ArrayList<MapData> getMaps() {
		return maps;
	}
	
	public String getSeriesName() {
		return seriesName;
	}
	
	public String getSeriesAuthor() {
		return seriesAuthor;
	}
	
	public String getSeriesImage() {
		return seriesImage;
	}
	
	public String getSeriesDescription() {
		return seriesDescrption;
	}
	
	public EmbedBuilder getSeriesEmbed() {
		return seriesEmbed;
	}
	
	public void regenerateEmbed() {
		this.seriesEmbed = null;
		this.seriesEmbed = new EmbedBuilder()
				.setTitle(this.seriesName)
				.setDescription(this.seriesDescrption)
				.setAuthor(this.seriesAuthor)
				.setColor(Color.ORANGE)
				.setFooter("(Link to series here!)")
				//.setImage(this.mapImage)
				.setThumbnail("http://i.imgur.com/XEmaKVo.png");
		if (this.seriesImage.startsWith("http")) {
			this.seriesEmbed.setImage(this.seriesImage);
		}
	}

	public void setSeriesName(String name) {
		this.seriesName = name;
		regenerateEmbed();
	}
	
	public void setSeriesAuthor(String author) {
		this.seriesAuthor = author;
		regenerateEmbed();
	}
	
	public void setSeriesImage(String image) {
		this.seriesImage = image;
		regenerateEmbed();
	}
	
	public void setSeriesDescription(String description) {
		this.seriesDescrption = description;
		regenerateEmbed();
	}
	
}
