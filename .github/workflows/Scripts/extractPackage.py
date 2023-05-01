from importlib.resources import path
from nis import match
import re
import hashlib
import os
import sys
from unitypackage_extractor.extractor import extractPackage

class checkPackage:

    def __init__(self, pathToPackage):
        self.pathToPackage = pathToPackage

    def extractPackage(self, pathToOuptut):
        extractPackage(self.pathToPackage, outputPath=pathToOuptut)


def main():
    package = checkPackage(sys.argv[1])
    strict_package = checkPackage(sys.argv[2])

    #testing integreity of files
    print("###################### \n Extracting files in unity packages \n ######################")
    package.extractPackage("./packageUnity")
    
    path_of_the_directory= 'packageUnity/'
    path_of_repo = "Assets/"
    
    files_to_not_check = ["package.json"]

    #checksum of files
    print("###################### \n testing integreity of files \n ######################")
    package.extractPackage("./packageUnity")
    for subdir, dirs, files in os.walk(path_of_repo):
        for file in files:
            file_in_package = os.path.join(*[path_of_the_directory, subdir,file])
            file_in_repo = os.path.join(subdir, file)
            if os.path.isfile(file_in_package) and os.path.isfile(file_in_repo):
                if file in files_to_not_check:
                    continue
                else:
                    if getHash(file_in_package) != getHash(file_in_repo):
                        print("❌ the file" , file, "is not the same")
                        sys.exit(5)
                    print("file ", file, "md5 check passed ✅")


        
    
def getHash(filePath):
    md5 = hashlib.md5()
    with open(filePath,'rb') as file:
        hash = file.read()
        md5.update(hash)
        return md5.hexdigest()
    
   
if __name__ == "__main__":
    main()