#include <fstream>
#include <iostream>
#include <string>
#include <regex>

using namespace std;

char img[1474560]; // the size of b.img is 1.44MB

void readFile();
void my_print(string s, bool use_red);
bool readable(char t);
bool isFile(string dir);
int searchFile(int startAddr, string filename);
void searchDir(int startAddr, string dir, bool l);
int isDir(string path);
int searchFileFromRoot(string dir);
extern "C"{
    void printRed(char*,int);
	void printWhite(char*,int);
}

int main(){
    readFile();
    while(true){
        my_print("input instruction\n", false);
        string input;
        getline(cin, input); // get input
        string word[100];
        int wordCount = 0;
        string currentWord = "";
        // split the input string into words
        for(int i=0;i<=input.length();i++){
            if((input[i]==' ' || i == input.length()) && currentWord.length()){
                word[wordCount++] = currentWord;
                currentWord = "";
                continue;
            }
            currentWord += input[i];
        }
        // judge input
        if(word[0]!="ls" && word[0]!="cat" && word[0]!="exit"){
            my_print("Illegal instruction!\n", false); // print error info
            continue;
        }
        // ls instruction
        if(word[0] == "ls"){
            bool illegal = false;
            bool haveDir = false;
            bool havel = false;
            string dir = "";
            for(int i=1;i<wordCount;i++){
                if(word[i][0] == '-'){
                    illegal = !regex_match(word[i], regex("-l+")); // input such as "-l", "-ll"... is legal
                    if(illegal) break;
                    havel = true;
                }
                else{
                    if(haveDir){
                        illegal = true;
                        break;
                    }
                    haveDir = true;
                    dir = word[i];
                }
            }
            if(illegal){
                my_print("Illegal ls instruction input!\n", false); // print error info
                continue;
            }
            if(!haveDir){
                searchDir(19*512, "", havel);
                continue;
            }
            if(dir[0] != '/') dir = '/' + dir;
            if(dir[dir.length()-1] == '/') dir = dir.substr(0, dir.length()-1);
            // remove ./ and process ../
            string allSubDir[100];
            int subDirNum = 0;
            string dirName = "";
            if(dir[0] != '/') dir = '/' + dir;
            bool validDir = true;
            for(int i=1;i<=dir.length();i++){
                if(i == dir.length() || dir[i] == '/'){
                    if(dirName != "."){
                        if(dirName == ".."){
                            if(subDirNum) subDirNum--;
                        }
                        else allSubDir[subDirNum++] = dirName;
                    }
                    dirName = "";
                    // check if the directory is correct
                    string tempDir = "";
                    for(int i=0;i<subDirNum;i++){
                        tempDir += ('/' + allSubDir[i]);
                    }
                    if(tempDir == "") continue;
                    else{
                        if(searchFileFromRoot(tempDir) == -1){
                            my_print("No such directory!\n", false);
                            validDir = false;
                            break;
                        }
                    }

                }
                else{
                    dirName += dir[i];
                }
            }
            if(!validDir) continue;
            dir = "";
            for(int i=0;i<subDirNum;i++){
                dir += ('/' + allSubDir[i]);
            }
            if(isDir(dir) != 1){
                my_print("No such directory!\n", false);
                continue;
            }
            int targetAddr = searchFileFromRoot(dir);
            searchDir(targetAddr, dir, havel);
        }
        // cat instruction
        if(word[0] == "cat"){
            if(wordCount != 2){
                my_print("Illegal cat instruction input!\n", false); // if wordCount!=2, then print error info
                continue;
            }
            string dir = word[1];
            if(dir[0] != '/') dir = '/' + dir;
            if(dir[dir.length()-1] == '/') dir = dir.substr(0, dir.length()-1);
            // remove ./ and process ../
            string allSubDir[100];
            int subDirNum = 0;
            string dirName = "";
            if(dir[0] != '/') dir = '/' + dir;
            bool validDir = true;
            for(int i=1;i<=dir.length();i++){
                if(i == dir.length() || dir[i] == '/'){
                    if(dirName != "."){
                        if(dirName == ".."){
                            if(subDirNum) subDirNum--;
                        }
                        else allSubDir[subDirNum++] = dirName;
                    }
                    dirName = "";
                    // check if the directory is correct
                    string tempDir = "";
                    for(int i=0;i<subDirNum;i++){
                        tempDir += ('/' + allSubDir[i]);
                    }
                    if(tempDir == "") continue;
                    else{
                        if(searchFileFromRoot(tempDir) == -1){
                            my_print("No such file!\n", false);
                            validDir = false;
                            break;
                        }
                    }

                }
                else{
                    dirName += dir[i];
                }
            }
            if(!validDir) continue;
            dir = "";
            for(int i=0;i<subDirNum;i++){
                dir += ('/' + allSubDir[i]);
            }
            if(isDir(dir) != 2){
                my_print("No such file!\n", false);
                continue;
            }
            int fileAddr = searchFileFromRoot(dir);
            if(fileAddr == -1){
                my_print("No such file!\n", false);
                continue;
            }
            int cluster = fileAddr / 512 - 33 + 2; // calculate the cluster number of the archive
            while(true){
                int i;
                string content = "";
                for (i = 0; img[fileAddr + i] && i < 512; i++) {
                    char c = (char)img[fileAddr + i];
                    content += c;
                }
                my_print(content, false);
                if(i != 512) break;
                // continue reading
                int fat;
                if(cluster % 2){ // if the cluster number is odd
                    // get next cluster number
                    fat = 513 + cluster / 2 * 3;
                    cluster = ((img[fat] >> 4) & 0x0f) | (img[fat + 1] << 4);
                }
                // if the cluster number is even
                else{
                    fat = 512 + cluster / 2 * 3;
                    cluster = ((img[fat + 1] & 0x0f) << 8) | img[fat];
                }
                fileAddr = 512 * 33 + (cluster - 2) * 512; // get next cluster address
            }
            my_print("\n", false); // print '\n' after reading
        }
        // exit instruction
        if(word[0] == "exit"){
            if(wordCount == 1) break;
            my_print("Illegal exit instruction input!", false);
            continue;
        }
    }
}

/**
 * @brief read image file and save it to the file array
 * @param
 * @note
 */
void readFile(){
    // read image with binary
    ifstream inputFile("/home/klee/Desktop/b.img", ios::binary);
    inputFile.read(img, 1024*1440);
    inputFile.close();
}

/**
 * @brief print string with red color or white color
 * @param string s
 * @param bool use_red
 * @note
 */
void my_print(string s, bool use_red){
    if(use_red){
        printRed((char*)s.c_str(), s.length());
    }
    else{
        printWhite((char*)s.c_str(), s.length());
    }
}

/**
 * @brief check if a char is readable
 * @param char t
 * @note
 */
bool readable(char t){
    if (t == ' ') {
        return true;
    }
    if (t >= 'a' && t <= 'z') {
        return true;
    }
    if (t >= 'A' && t <= 'Z') {
        return true;
    }
    if (t >= '0' && t <= '9') {
        return true;
    }
    return false;
}
/**
 * @brief check if a dir is a single file
 * @param string dir
 * @note
 */
bool isFile(string dir){
    for(int i=0;i<dir.length();i++){
        if(dir[i] == '/') return false;
    }
    return true;
}

/**
 * @brief find file from given start address
 * @param int startAddr
 * @param string filename
 * @note
 */
int searchFile(int startAddr, string filename){
    int currentAddr = startAddr;
    if(filename[0] == '/') filename = filename.substr(1);
    bool flag = isFile(filename);
    while(flag){
        if(!img[currentAddr]) break;
        // check if the file is readable
        bool readableFile = true;
        for(int i=0;i<11;i++){
            if(!readable(img[currentAddr + i])){
                readableFile = false;
                currentAddr +=32;
                break;
            }
        }
        if(!readableFile) continue;
        // if target filename is a file
        if(img[currentAddr + 11] == 0x10){ // is a file
            string name = "";
            // get current file name
            for(int i=0;i<11;i++){
                // filter the space
                if(img[currentAddr + i] != ' ') name += char(img[currentAddr + i]);
            }
            if(name == filename) return 512*33+(img[currentAddr+26]-2)*512;
        }
        // if target filename is an archive
        if(img[currentAddr + 11] == 0x20){ // is an archive
            string name = "";
            for(int i=0;i<8;i++){
                if(img[currentAddr + i] != ' ') name += char(img[currentAddr + i]);
            }
            if(img[currentAddr + 8] != ' '){
                name += '.';
                for(int i=8;i<11;i++){
                    if(img[currentAddr + i] != ' ') name += char(img[currentAddr + i]);
                }
            }
            if(name == filename) return 512*33+(img[currentAddr+26]-2)*512;
        }
        currentAddr += 32;
    }
    return -1;
}

/**
 * @brief find file from given start address and directory
 * @param int startAddr
 * @param string dir
 * @param bool l
 * @note
 */
void searchDir(int startAddr, string dir, bool l){
    string allArchive[1000];
    int archiveSize[1000];
    string allFile[1000];
    int fileAddr[1000];
    int archiveCount = 0, fileCount = 0;
    int currentAddr = startAddr;
    while(true){
        if(img[currentAddr] == 0) break; // end
        // check if the file is readable
        bool readableFile = true;
        for(int i=0;i<11;i++){
            if(!readable(img[currentAddr + i])){
                readableFile = false;
                currentAddr +=32;
                break;
            }
        }
        if(!readableFile) continue;
        if(img[currentAddr + 11] == 0x10){ // is a file
            string name = "";
            // get current file name
            for(int i=0;i<11;i++){
                // filter the space
                if(img[currentAddr + i] != ' ') name += char(img[currentAddr + i]);
            }
            // save current file name and start address
            allFile[fileCount] = name;
            fileAddr[fileCount++] = 512*33+(img[currentAddr+26]-2)*512;
        }
        else if(img[currentAddr + 11] == 0x20){
            string name = "";
            for(int i=0;i<8;i++){
                if(img[currentAddr + i] != ' ') name += char(img[currentAddr + i]);
            }
            if(img[currentAddr + 8] != ' '){
                name += '.';
                for(int i=8;i<11;i++){
                    if(img[currentAddr + i] != ' ') name += char(img[currentAddr + i]);
                }
            }
            // save current archive name and size
            allArchive[archiveCount] = name;
            archiveSize[archiveCount++] = img[currentAddr+28]+256*img[currentAddr+29];
        }
        currentAddr += 32;
    }

    // if the instruction include "-l"
    if(l){
        my_print(dir + "/ " + to_string(fileCount) + " " + to_string(archiveCount) + ":\n", false);
        if(dir != "") my_print(".\n..\n", true);
        // first, print all archives and their sizes;
        for(int i=0;i<archiveCount;i++){
            my_print(allArchive[i] + " " + to_string(archiveSize[i]) + "\n", false);
        }
        for(int i=0;i<fileCount;i++){
            // count the number of files and archives in subdirectory
            int tempAddr = fileAddr[i];
            int subFile = 0, subArchive = 0;
            while(img[tempAddr]){
                bool readableFile = true;
                for(int i=0;i<11;i++){
                    if(!readable(img[tempAddr + i])){
                        readableFile = false;
                        tempAddr +=32;
                        break;
                    }
                }
                if(!readableFile) continue;
                if(img[tempAddr + 11] == 0x10) subFile++;
                if(img[tempAddr + 11] == 0x20) subArchive++;
                tempAddr += 32;
            }
            my_print(allFile[i], true);
            my_print(" " + to_string(subFile)+ " " + to_string(subArchive) + "\n", false);
        }
    }
    else{
        my_print(dir + "/:\n", false);
        if(dir != "") my_print(".  ..  ", true);
        // print all archives
        for(int i=0;i<archiveCount;i++){
            my_print(allArchive[i]+"  ", false);
        }
        // print all files
        for(int i=0;i<fileCount;i++){
            my_print(allFile[i]+"  ", true);
        }
        my_print("\n", false);

    }
    my_print("\n", true);

    // find subdirectory recursively
    for(int i=0;i<fileCount;i++){
        if(allFile[i] != "." && allFile[i] != ".." ){
            searchDir(fileAddr[i], dir + "/" + allFile[i], l);
        }
    }
}

/**
 * @brief find file from root directory
 * @param string dir
 * @note
 */
int searchFileFromRoot(string dir){

    // find directory address
    int targetAddr = 19*512;
    string currentDir = dir;
    while(currentDir != ""){
        string targetFile = "";
        int i;
        for(i=1;i<currentDir.length();i++){
            if(currentDir[i] == '/') break;
            targetFile += currentDir[i];
        }
        currentDir = currentDir.substr(i);
        targetAddr = searchFile(targetAddr, targetFile);
        if(targetAddr == -1) break;
    }
    return targetAddr;
}

/**
 * @brief check if a path is a file or a directory
 * @param string path
 * @note
 */
int isDir(string path){
    int targetAddr = searchFileFromRoot(path);
    if(img[targetAddr + 11] == 0x10) return 1;
    if(img[targetAddr + 11] == 0x20) return 2;
    return 0;
}
