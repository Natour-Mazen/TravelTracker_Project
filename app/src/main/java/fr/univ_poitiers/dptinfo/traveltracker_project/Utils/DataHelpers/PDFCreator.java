package fr.univ_poitiers.dptinfo.traveltracker_project.Utils.DataHelpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;

public class PDFCreator {
    private static final String LOG_TAG = "PDFCreator";

    // Constants for page size and text properties
    private static final int PAGE_WIDTH = 600;
    private static final int PAGE_HEIGHT = 800;
    private static final int PAGE_NUMBER = 1;
    private static final int MARGIN = 50;
    private static final int TITLE_TEXT_SIZE = 30;
    private static final int CONTENT_TEXT_SIZE = 20;
    private static final int LINE_SPACING = 10;


    // Method to create a PDF document from trip information
    public static Uri createPDF(Context context, Trip trip) {
        // Create a new PDF document
        PdfDocument document = createDocument(trip);
        // Save the document and get its file
        File file = saveDocument(context, document, trip);
        // If file creation was successful, return its URI using a FileProvider
        if (file != null) {
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        } else {
            return null;
        }
    }

    // Method to create a PDF document containing trip information
    private static PdfDocument createDocument(Trip trip) {
        // Create a new PDF document
        PdfDocument document = new PdfDocument();
        // Define page information
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, PAGE_NUMBER).create();
        // Start a new page in the document
        PdfDocument.Page page = document.startPage(pageInfo);
        // Get the canvas for drawing
        Canvas canvas = page.getCanvas();
        // Create paint object for drawing
        Paint paint = createPaint();
        // Draw trip information on the canvas
        drawTripInformation(canvas, paint, trip);
        // Finish the page
        document.finishPage(page);
        // Return the created document
        return document;
    }

    // Method to create a Paint object for drawing
    private static Paint createPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        return paint;
    }

    // Method to draw trip information on the canvas
    private static void drawTripInformation(Canvas canvas, Paint paint, Trip trip) {
        int yPos = MARGIN;

        // Title
        paint.setTextSize(TITLE_TEXT_SIZE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Trip Information", MARGIN, yPos, paint);
        yPos += TITLE_TEXT_SIZE + LINE_SPACING;

        // Content
        paint.setTextSize(CONTENT_TEXT_SIZE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText("Name: " + trip.getName(), MARGIN, yPos, paint);
        yPos += CONTENT_TEXT_SIZE + LINE_SPACING;
        canvas.drawText("Country: " + trip.getCountry(), MARGIN, yPos, paint);
        yPos += CONTENT_TEXT_SIZE + LINE_SPACING;
        canvas.drawText("City: " + trip.getCity(), MARGIN, yPos, paint);
        yPos += CONTENT_TEXT_SIZE + LINE_SPACING;
        canvas.drawText("Departure Date: " + trip.getDepartureDate(), MARGIN, yPos, paint);
        yPos += CONTENT_TEXT_SIZE + LINE_SPACING;
        canvas.drawText("Ambiance Rating: " + trip.getAmbianceRating(), MARGIN, yPos, paint);
        yPos += CONTENT_TEXT_SIZE + LINE_SPACING;
        canvas.drawText("Natural Beauty Rating: " + trip.getNaturalBeautyRating(), MARGIN, yPos, paint);
        yPos += CONTENT_TEXT_SIZE + LINE_SPACING;
        canvas.drawText("Security Rating: " + trip.getSecurityRating(), MARGIN, yPos, paint);
        yPos += CONTENT_TEXT_SIZE + LINE_SPACING;
        canvas.drawText("Accommodation Rating: " + trip.getAccommodationRating(), MARGIN, yPos, paint);
        yPos += CONTENT_TEXT_SIZE + LINE_SPACING;
        canvas.drawText("Human Interaction Rating: " + trip.getHumanInteractionRating(), MARGIN, yPos, paint);
        yPos += CONTENT_TEXT_SIZE + LINE_SPACING;
        canvas.drawText("Planned Budget: " + trip.getPlannedBudget(), MARGIN, yPos, paint);
        yPos += CONTENT_TEXT_SIZE + LINE_SPACING;
        canvas.drawText("Actual Budget: " + trip.getActualBudget(), MARGIN, yPos, paint);
    }


    // Method to save the PDF document
    private static File saveDocument(Context context, PdfDocument document, Trip trip) {
        try {
            // Get the cache directory
            File cacheDir = context.getCacheDir();
            // Create a file in the cache directory with trip name as filename
            File file = new File(cacheDir, "trip_" + trip.getName() + ".pdf");
            // Create an output stream to write to the file
            FileOutputStream outputStream = new FileOutputStream(file);
            // Write the document content to the output stream
            document.writeTo(outputStream);
            // Close the document and output stream
            document.close();
            outputStream.close();
            // Return the created file
            return file;
        } catch (IOException e) {
            // Log any errors that occur during file saving
            LogHelper.logError(LOG_TAG, "Error saving document: " + e.getMessage());
            return null;
        }
    }
}
