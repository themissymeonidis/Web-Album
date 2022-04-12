package Servlets;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TiffTagConstants;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
/**
 *
 * @author kosta
 */
public class MetadataExtraction {
     public static void main(String[] args) {
      File file = new File("/home/muvox/NetBeansProjects/bisakha-datta-2qOTf5qx2_4-unsplash.jpg");
      System.out.println(file);
      try { 
             metadataExample(file);
         } catch (ImageReadException ex) {
             Logger.getLogger(MetadataExtraction.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(MetadataExtraction.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

    public static String[] metadataExample(final File file) throws ImageReadException,
        IOException {
    // get all metadata stored in EXIF format (ie. from JPEG or TIFF).
    final IImageMetadata metadata =  Sanselan.getMetadata(file);
    String[] export = new String[255];
    double longitude_double = 0.0;
    double latitude_double = 0.0;
    String longitude = null;
    String latitude = null;


    if (metadata instanceof JpegImageMetadata) {
        final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

        final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
        if (null != exifMetadata) {
            final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
            if (null != gpsInfo) {
                final String gpsDescription = gpsInfo.toString();
                longitude_double = gpsInfo.getLongitudeAsDegreesEast();
                latitude_double = gpsInfo.getLatitudeAsDegreesNorth();
                System.out.println(longitude_double);
                System.out.println(latitude_double);
            }else {
                longitude_double = 0.0;
                latitude_double = 0.0;
            }
        }

        longitude = String.valueOf(longitude_double);
        latitude = String.valueOf(latitude_double);
        System.out.println(jpegMetadata);

        System.out.println("file: " + file.getPath());
        System.out.println(jpegMetadata);
        export[0] = stasino(jpegMetadata, ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
        export[1] = stasino(jpegMetadata, TiffTagConstants.TIFF_TAG_MAKE);
        export[2] = stasino(jpegMetadata, TiffTagConstants.TIFF_TAG_MODEL);
        export[3] = stasino(jpegMetadata, ExifTagConstants.EXIF_TAG_EXIF_IMAGE_WIDTH);
        export[4] = stasino(jpegMetadata, ExifTagConstants.EXIF_TAG_EXIF_IMAGE_LENGTH);
        export[5] = longitude;
        export[6] = latitude;

        System.out.println(export);
        // print out various interesting EXIF tags.
//        printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_XRESOLUTION);
//        printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_DATE_TIME);
//        printTagValue(jpegMetadata,
//                ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
//        //printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED);
//        printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_ISO);
//        printTagValue(jpegMetadata,
//                ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE);
//        printTagValue(jpegMetadata,
//                ExifTagConstants.EXIF_TAG_APERTURE_VALUE);
//        printTagValue(jpegMetadata,
//                ExifTagConstants.EXIF_TAG_BRIGHTNESS_VALUE);
//        printTagValue(jpegMetadata,
//                GPSTagConstants.GPS_TAG_GPS_LATITUDE_REF);
//        printTagValue(jpegMetadata, GPSTagConstants.GPS_TAG_GPS_LATITUDE);
//        printTagValue(jpegMetadata,
//                GPSTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
//        printTagValue(jpegMetadata, GPSTagConstants.GPS_TAG_GPS_LONGITUDE);
//
//        System.out.println();
//
        // simple interface to GPS data
       
//  
//        final TiffField gpsLatitudeRefField = jpegMetadata.findEXIFValue(GPSTagConstants.GPS_TAG_GPS_LATITUDE_REF);
//        final TiffField gpsLatitudeField = jpegMetadata.findEXIFValue(GPSTagConstants.GPS_TAG_GPS_LATITUDE);
//        final TiffField gpsLongitudeRefField = jpegMetadata.findEXIFValue(GPSTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
//        final TiffField gpsLongitudeField = jpegMetadata.findEXIFValue(GPSTagConstants.GPS_TAG_GPS_LONGITUDE);
//        if (gpsLatitudeRefField != null && gpsLatitudeField != null
//                && gpsLongitudeRefField != null
//                && gpsLongitudeField != null) {
//            // all of these values are strings.
//            final String gpsLatitudeRef = (String) gpsLatitudeRefField.getValue();
//            final RationalNumber gpsLatitude[] = (RationalNumber[]) (gpsLatitudeField
//                    .getValue());
//            final String gpsLongitudeRef = (String) gpsLongitudeRefField
//                    .getValue();
//            final RationalNumber gpsLongitude[] = (RationalNumber[]) gpsLongitudeField
//                    .getValue();
//
//            final RationalNumber gpsLatitudeDegrees = gpsLatitude[0];
//            final RationalNumber gpsLatitudeMinutes = gpsLatitude[1];
//            final RationalNumber gpsLatitudeSeconds = gpsLatitude[2];
//
//            final RationalNumber gpsLongitudeDegrees = gpsLongitude[0];
//            final RationalNumber gpsLongitudeMinutes = gpsLongitude[1];
//            final RationalNumber gpsLongitudeSeconds = gpsLongitude[2];
//  
//            // This will format the gps info like so:
//            //
//            // gpsLatitude: 8 degrees, 40 minutes, 42.2 seconds S
//            // gpsLongitude: 115 degrees, 26 minutes, 21.8 seconds E
//
//            System.out.println("    " + "GPS Latitude: "
//                    + gpsLatitudeDegrees.toDisplayString() + " degrees, "
//                    + gpsLatitudeMinutes.toDisplayString() + " minutes, "
//                    + gpsLatitudeSeconds.toDisplayString() + " seconds "
//                    + gpsLatitudeRef);
//            System.out.println("    " + "GPS Longitude: "
//                    + gpsLongitudeDegrees.toDisplayString() + " degrees, "
//                    + gpsLongitudeMinutes.toDisplayString() + " minutes, "
//                    + gpsLongitudeSeconds.toDisplayString() + " seconds "
//                    + gpsLongitudeRef);
//
//        }
//
//        System.out.println();
//
//        final List<Item> items = jpegMetadata.getItems();
//        for (int i = 0; i < items.size(); i++) {
//            final Item item = items.get(i);
//            System.out.println("    " + "item: " + item);
//        }
//
//        System.out.println();
    }
         return export;
  }

  private static void printTagValue(final JpegImageMetadata jpegMetadata,
          final TagInfo tagInfo) {
      final TiffField field = jpegMetadata.findEXIFValue(tagInfo);
      if (field == null) {
          System.out.println(tagInfo.name + ": " + "Not Found.");
      } else {
          System.out.println(tagInfo.name + ": "
                + field.getValueDescription());
      }
  }
  public static String stasino (final JpegImageMetadata jpegMetadata,
          final TagInfo tagInfo) {
          String giwrgos = "null";
          final TiffField field = jpegMetadata.findEXIFValue(tagInfo);
          if (field == null) {
          System.out.println(tagInfo.name + ": " + "Not Found.");
      } else {
              giwrgos = field.getValueDescription();

      }
      
          return giwrgos;
  }
  
}
   