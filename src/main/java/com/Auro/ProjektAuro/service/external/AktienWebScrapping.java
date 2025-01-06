package com.Auro.ProjektAuro.service.external;

import java.time.LocalTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class AktienWebScrapping {

    public boolean isRegularMarket() {
        LocalTime boersenBeginn = LocalTime.of(15, 30);
        LocalTime aktuelleZeit = LocalTime.now();
        return aktuelleZeit.isAfter(boersenBeginn);
    }

    public AktienScrappingDatenLive reloadStockPriceMarket(String ticker) {
        try {
            //wenn keine Zahlen angezeigt werden, dann ist es entweder "pre" oder "post". Das ändert sich ja immerwieder
            //ein Test hinzugüfen, dass das auf die änderungen reagieren kann
            String changePercentField = isRegularMarket() ? "regularMarketChangePercent" : "preMarketChangePercent";
            String changeField = isRegularMarket() ? "regularMarketChange" : "preMarketChange";
            String priceField = isRegularMarket() ? "regularMarketPrice" : "preMarketPrice";

            String url = "https://de.finance.yahoo.com/quote/" + ticker + "/";
            Document document = Jsoup.connect(url)
                                     .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                                     .get();

            Element marketChangePercentElement = document.selectFirst("fin-streamer[data-field=" + changePercentField + "]");
            Element marketChangeElement = document.selectFirst("fin-streamer[data-field=" + changeField + "]");
            Element marketPriceElement = document.selectFirst("fin-streamer[data-field=" + priceField + "]");

            if (marketPriceElement == null) {
                marketChangePercentElement = document.selectFirst("fin-streamer[data-field=regularMarketChangePercent]");
                marketChangeElement = document.selectFirst("fin-streamer[data-field=regularMarketChange]");
                marketPriceElement = document.selectFirst("fin-streamer[data-field=regularMarketPrice]");
            } else {
                
            }

            Double marketPrice = parseElementToDouble(marketPriceElement);
            Double marketChangePercent = parseElementToDouble(marketChangePercentElement);
            Double marketChange = parseElementToDouble(marketChangeElement);

            return new AktienScrappingDatenLive(marketPrice, 
                                                marketChangePercent, 
                                                marketChange, 
                                                ticker);

        } catch (Exception e) {
            System.err.println("Fehler beim Scraping: " + e.getMessage());
            return null;
        }
    }

    private Double parseElementToDouble(Element element) {
        if (element != null && element.hasAttr("data-value")) {
            return Math.round(Double.parseDouble(element.attr("data-value")) * 100.0) / 100.0;
        }
        return null;
    }

    public AktienScrappingDatenInfos scrapeStockInfos(String ticker) {
        try {
            String url = "https://de.finance.yahoo.com/quote/" + ticker + "/";
            Document document = Jsoup.connect(url).get();

            Element companyNameElement = document.selectFirst("h1.yf-xxbei9");
            Element regularMarketDayRangeElement = document.selectFirst("fin-streamer[data-field=regularMarketDayRange]");
            Element fiftyTwoWeekRangeElement = document.selectFirst("fin-streamer[data-field=fiftyTwoWeekRange]");
            Element regularMarketVolumeElement = document.selectFirst("fin-streamer[data-field=regularMarketVolume]");
            Element marketCapElement = document.selectFirst("fin-streamer[data-field=marketCap]");
            Element trailingPEElement = document.selectFirst("fin-streamer[data-field=trailingPE]");
            Element targetMeanPriceElement = document.selectFirst("fin-streamer[data-field=targetMeanPrice]");
            Element expectedDividendElement = document.selectFirst("li:has(span[title='Erwartete Dividende & Rendite']) span.value.yf-dudngy");

            String companyName = (companyNameElement != null) ? companyNameElement.text() : "Unternehmensname nicht gefunden";
            String regularMarketDayRange = (regularMarketDayRangeElement != null) ? regularMarketDayRangeElement.attr("data-value") : "regularMarketChangePercent nicht gefunden";
            String fiftyTwoWeekRange = (fiftyTwoWeekRangeElement != null) ? fiftyTwoWeekRangeElement.attr("data-value") : "fiftyTwoWeekRange nicht gefunden";
            String regularMarketVolume = (regularMarketVolumeElement != null) ? regularMarketVolumeElement.attr("data-value") : "regularMarketVolume nicht gefunden";
            String marketCap = (marketCapElement != null) ? marketCapElement.attr("data-value") : "marketCap nicht gefunden";
            String trailingPE = (trailingPEElement != null) ? trailingPEElement.attr("data-value") : "trailingPE nicht gefunden";
            String targetMeanPrice = (targetMeanPriceElement != null) ? targetMeanPriceElement.attr("data-value") : "targetMeanPrice nicht gefunden";
            String expectedDividend = (expectedDividendElement != null) ? expectedDividendElement.text() : "expectedDividend nicht gefunden";

            return new AktienScrappingDatenInfos(companyName, 
                                                    regularMarketDayRange, 
                                                    fiftyTwoWeekRange, 
                                                    regularMarketVolume, 
                                                    marketCap, 
                                                    trailingPE, 
                                                    targetMeanPrice, 
                                                    expectedDividend, 
                                                    ticker);
        } catch (Exception e) {
            System.err.println("Fehler beim Scraping: " + e.getMessage());
            return null;
        }
    }
}
