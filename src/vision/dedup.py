"""Image Deduplication using FLANN K nearest neighbours"""

import argparse
import imutils
import numpy as np
from matplotlib import pyplot as plt
import os
import cv2


folder_path='./duplicate_images/'

# Create SIFT Object
sift = cv2.xfeatures2d.SIFT_create()
# FLANN parameters
FLANN_INDEX_KDTREE = 0
index_params = dict(algorithm = FLANN_INDEX_KDTREE, trees = 5)
search_params = dict(checks=50)  

flann = cv2.FlannBasedMatcher(index_params,search_params)

updated_folder = os.listdir(folder_path)
del_img_arr = []

for i in updated_folder:
  i1_name=i
  if i1_name in del_img_arr:
    continue
  i=folder_path+i
  img1_temp=cv2.imread(i)
  kp1_temp, des1_temp = sift.detectAndCompute(img1_temp,None)
  for j in updated_folder:
    i2_name=j
    if i2_name in del_img_arr:
        continue
    j=folder_path+j
    print(i1_name,i2_name)
    if i != j:
      img2_temp=cv2.imread(j)
      kp2_temp, des2_temp = sift.detectAndCompute(img2_temp,None)
      #K nearest matches
      matches_temp = flann.knnMatch(des1_temp,des2_temp,k=2)

      good_temp = []
      # ratio test
      for match1,match2 in matches_temp:
        if match1.distance < 0.7*match2.distance:
          good_temp.append([match1])
      number_keypoints_temp = 0

      if len(kp1_temp) >= len(kp2_temp):
        number_keypoints_temp = len(kp1_temp)
        del_img= i2_name
      else:
        number_keypoints_temp = len(kp2_temp)
        del_img= i1_name
      print(number_keypoints_temp,len(good_temp))

      percentage_similarity = len(good_temp) / number_keypoints_temp * 100
      print("Similarity: " + str(percentage_similarity))

      if percentage_similarity >= 6:
        print('Images ' +i1_name+ ' and ' +i2_name+ ' are duplicates and we can delete '+del_img + "\n" )
        os.remove("./duplicate_images/"+del_img)
        del_img_arr.append(del_img)
        updated_folder = os.listdir(folder_path)
      else:
        print('Images ' +i1_name+ ' and ' +i2_name+ ' are not duplicates' + "\n")
        updated_folder = os.listdir(folder_path)
        