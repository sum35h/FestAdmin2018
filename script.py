# -*- coding: utf-8 -*-
import xlrd
import xlwt
from xlutils.copy import copy
 


 
#----------------------------------------------------------------------

    

#File to be copied
wb = xlrd.open_workbook("s1.xls") #Add file name
sheet = wb.sheet_by_index(1) #Add Sheet name
 
#File to be pasted into
template = copy(xlrd.open_workbook("s2.xls"))
temp_sheet = template.get_sheet(1)
#temp_sheet =  template.sheet_by_name("Sheet 1") #Add Sheet name

def createData():
    print("Processing...")
    selectedRange = copyRange(0,7,1,17,sheet)
    pasteRange(2,7,3,17,temp_sheet,selectedRange)
    template.save("www.xls")
    print("Range copied and pasted!")




#Copy range of cells as a nested list
#Takes: start cell, end cell, and sheet you want to copy from.
def copyRange(startCol, startRow, endCol, endRow, sheet):
    rangeSelected = []
    print("inside copy")
    #Loops through selected Rows
    for i in range(startRow,endRow + 1,1):
        #Appends the row to a RowSelected list
        #print(i)
        rowSelected = []
        for j in range(startCol,endCol+1,1):
            rowSelected.append(sheet.cell(i, j).value)
            print(sheet.cell(i, j).value)
        #Adds the RowSelected List and nests inside the rangeSelected
        rangeSelected.append(rowSelected)
 
    return rangeSelected




#Paste range
#Paste data from copyRange into template sheet
def pasteRange(startCol, startRow, endCol, endRow, sheetReceiving,copiedData):
    countRow = 0
    for i in range(startRow,endRow+1,1):
        countCol = 0
        for j in range(startCol,endCol+1,1):
            sheetReceiving.write(i,j,copiedData[countRow][countCol])
            countCol += 1
        countRow += 1
        
        
        
