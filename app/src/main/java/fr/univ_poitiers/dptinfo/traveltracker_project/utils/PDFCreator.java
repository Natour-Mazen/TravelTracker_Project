package fr.univ_poitiers.dptinfo.traveltracker_project.utils;

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

public class PDFCreator {

    private static final int PAGE_WIDTH = 300;
    private static final int PAGE_HEIGHT = 400;
    private static final int PAGE_NUMBER = 1;
    private static final int TEXT_SIZE = 25;
    private static final int TEXT_START_POSITION = 10;
    private static final String LOG_TAG = "PDFCreator" ;

    public static Uri createPDF(Context context, Trip trip) {
        PdfDocument document = createDocument(trip);
        File file = saveDocument(context, document, trip);
        if (file != null) {
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        } else {
            return null;
        }
    }


    private static PdfDocument createDocument(Trip trip) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, PAGE_NUMBER).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = createPaint();

        drawTripInformation(canvas, paint, trip);

        document.finishPage(page);
        return document;
    }

    private static Paint createPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        return paint;
    }

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

    private static File saveDocument(Context context, PdfDocument document, Trip trip) {
        try {
            File cacheDir = context.getCacheDir();
            File file = new File(cacheDir, "trip_" + trip.getName() + ".pdf");

            FileOutputStream outputStream = new FileOutputStream(file);
            document.writeTo(outputStream);
            document.close();
            outputStream.close();

            return file;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error saving document: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }



    private static Uri insertFileIntoMediaStore(Context context, File file) {
        Uri contentUri = null;
        if (file != null) {
            ContentValues contentValues = createContentValues(file);
            ContentResolver contentResolver = context.getContentResolver();
            contentUri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues);
            writeToFile(contentResolver, contentUri, file);
        }
        return contentUri;
    }

    private static ContentValues createContentValues(File file) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, file.getName());
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);
        return contentValues;
    }

    private static void writeToFile(ContentResolver contentResolver, Uri contentUri, File file) {
        try {
            InputStream inputStream = Files.newInputStream(file.toPath());
            OutputStream outputStream = contentResolver.openOutputStream(contentUri);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
