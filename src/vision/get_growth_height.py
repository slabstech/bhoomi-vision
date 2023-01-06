""" Logic to Measure Plant Height"""

import argparse
import imutils.contours
import cv2
import ffmpeg
import os

def get_height():
    greyscale = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    greyscale = cv2.GaussianBlur(greyscale, (7, 7), 0)

    # Detect edges and close gaps
    canny_output = cv2.Canny(greyscale, 50, 100)
    canny_output = cv2.dilate(canny_output, None, iterations=1)
    canny_output = cv2.erode(canny_output, None, iterations=1)

    # Get the contours of the shapes, sort l-to-r and create boxes
    #print(cv2.findContours(canny_output, cv2.RETR_EXTERNAL,
       #                             cv2.CHAIN_APPROX_SIMPLE))
    contours, _ = cv2.findContours(canny_output, cv2.RETR_EXTERNAL,
                                    cv2.CHAIN_APPROX_SIMPLE)
    if len(contours) < 2:
        print("Couldn't detect two or more objects")
        exit(0)

    (contours, _) = imutils.contours.sort_contours(contours)
    contours_poly = [None]*len(contours)
    boundRect = [None]*len(contours)
    for i, c in enumerate(contours):
        contours_poly[i] = cv2.approxPolyDP(c, 3, True)
        boundRect[i] = cv2.boundingRect(contours_poly[i])

    output_image = image.copy()
    mmPerPixel = 2 / boundRect[0][2]
    highestRect = 1000
    lowestRect = 0

    for i in range(1, len(contours)):

        # Too smol?
        if boundRect[i][2] < 50 or boundRect[i][3] < 50:
            continue

        # The first rectangle is our control, so set the ratio
        if highestRect > boundRect[i][1]:
            highestRect = boundRect[i][1]
        if lowestRect < (boundRect[i][1] + boundRect[i][3]):
            lowestRect = (boundRect[i][1] + boundRect[i][3])

        # Create a boundary box
        cv2.rectangle(output_image, (int(boundRect[i][0]), int(boundRect[i][1])),
                    (int(boundRect[i][0] + boundRect[i][2]),
                    int(boundRect[i][1] + boundRect[i][3])), (255, 0, 0), 2)

    # Calculate the size of our plant
    plantHeight = (lowestRect - highestRect) * mmPerPixel
    print("Plant height is {0:.0f}mm".format(plantHeight))

    # Resize and display the image (press key to exit)
    resized_image = cv2.resize(output_image, (1280, 720))
    cv2.imshow("Image", resized_image)
    cv2.waitKey(0)
    return plantHeight

def getFrame(sec):
    vidcap.set(cv2.CAP_PROP_POS_MSEC,sec*1000)
    hasFrames,image = vidcap.read()
    if hasFrames:
        cv2.imwrite("frames/image"+str(count)+".jpg", image)     # save frame as JPG file
    return hasFrames

# Get our options
parser = argparse.ArgumentParser(description='Object height measurement')
parser.add_argument("-i", "--image", type=str, required=False,
                    help="file to process")
parser.add_argument("-v", "--video", type=str, required=False,
                    help="Video File")
args = vars(parser.parse_args())

# Read in the image
try:
    image = cv2.imread(args["image"])
    get_height()
except:
    vidcap = cv2.VideoCapture(args["video"])
    sec = 0
    frameRate = 2 #//it will capture image in each 0.5 second
    count=1
    success = getFrame(sec)
    while success:
        count = count + 1
        sec = sec + frameRate
        sec = round(sec, 2)
        success = getFrame(sec)
    print("Frames Extracted.... Calculating Height")
    f = open("FrameHeightList.txt", "w")
    for filename in os.listdir("./frames/"):
        print(filename)
        if filename.endswith(".jpg"):
            image = cv2.imread("./frames/" + filename)
            f.write(str(get_height())+"\n")
    f.close()



    
