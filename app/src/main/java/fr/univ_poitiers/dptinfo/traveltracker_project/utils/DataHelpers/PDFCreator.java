package fr.univ_poitiers.dptinfo.traveltracker_project.utils.DataHelpers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import fr.univ_poitiers.dptinfo.traveltracker_project.DataBase.Entities.Trip;
import fr.univ_poitiers.dptinfo.traveltracker_project.utils.LogHelper;

public class PDFCreator {
    private static final String LOG_TAG = "PDFCreator";

    // Constants for page size and text properties
    private static final int PAGE_WIDTH = 300;
    private static final int PAGE_HEIGHT = 400;
    private static final int PAGE_NUMBER = 1;
    private static final int TEXT_SIZE = 25;
    private static final int TEXT_START_POSITION = 10;


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
        int yPos = TEXT_SIZE;
        canvas.drawText("Name: " + trip.getName(), TEXT_START_POSITION, yPos, paint);
        yPos += TEXT_SIZE;
        canvas.drawText("Country: " + trip.getCountry(), TEXT_START_POSITION, yPos, paint);
        yPos += TEXT_SIZE;
        canvas.drawText("City: " + trip.getCity(), TEXT_START_POSITION, yPos, paint);
        yPos += TEXT_SIZE;
        canvas.drawText("Departure Date: " + trip.getDepartureDate(), TEXT_START_POSITION, yPos, paint);
        yPos += TEXT_SIZE;
        canvas.drawText("Ambiance Rating: " + trip.getAmbianceRating(), TEXT_START_POSITION, yPos, paint);
        yPos += TEXT_SIZE;
        canvas.drawText("Natural Beauty Rating: " + trip.getNaturalBeautyRating(), TEXT_START_POSITION, yPos, paint);
        yPos += TEXT_SIZE;
        canvas.drawText("Security Rating: " + trip.getSecurityRating(), TEXT_START_POSITION, yPos, paint);
        yPos += TEXT_SIZE;
        canvas.drawText("Accommodation Rating: " + trip.getAccommodationRating(), TEXT_START_POSITION, yPos, paint);
        yPos += TEXT_SIZE;
        canvas.drawText("Human Interaction Rating: " + trip.getHumanInteractionRating(), TEXT_START_POSITION, yPos, paint);
        yPos += TEXT_SIZE;
        canvas.drawText("Planned Budget: " + trip.getPlannedBudget(), TEXT_START_POSITION, yPos, paint);
        yPos += TEXT_SIZE;
        canvas.drawText("Actual Budget: " + trip.getActualBudget(), TEXT_START_POSITION, yPos, paint);
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
