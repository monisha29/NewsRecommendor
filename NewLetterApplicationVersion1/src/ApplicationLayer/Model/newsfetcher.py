import requests
import json
import pyodbc
import re
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
stop_words = set(stopwords.words('english'))

emoticons_str = r"""
   (?:
       [:=;] # Eyes
       [oO\-]? # Nose (optional)
       [D\)\]\(\]/\\OpP] # Mouth
   )"""


regex_str = [
   emoticons_str,
   r'<[^>]+>',  # HTML tags
   r'(?:@[\w_]+)',  # @-mentions
   r"(?:\#+[\w_]+[\w\'_\-]*[\w_]+)",  # hash-tags
   r'http[s]?://(?:[a-z]|[0-9]|[$-_@.&amp;+]|[!*\(\),]|(?:%[0-9a-f][0-9a-f]))+',  # URLs

   r'(?:(?:\d+,?)+(?:\.?\d+)?)',  # numbers
   r"(?:[a-z][a-z'\-_]+[a-z])",  # words with - and '
   r'(?:[\w_]+)',  # other words
   r'(?:\S)'  # anything else
]

tokens_re = re.compile(r'(' + '|'.join(regex_str) + ')', re.VERBOSE | re.IGNORECASE)
emoticon_re = re.compile(r'^' + emoticons_str + '$', re.VERBOSE | re.IGNORECASE)


def tokenize(s):
   return tokens_re.findall(s)

def computeTF(wordDict ,bow):
    tfDict ={}
    bowcount =len(bow)
    for word, count in wordDict.items():
        tfDict[word]=count/float(bowcount)
    return tfDict


def computeIDF(docList):
    import math
    idfDict = {}
    N =len(docList)

    idfDict = dict.fromkeys(docList[0].keys(),0)
    for doc in docList:
        for word, val in doc.items():
            if val>0:
                idfDict[word] +=1

    for word, val in idfDict.items():
        idfDict[word] =math.log(N / float(val))

    return idfDict


def computeTFIDF (tfBow , idfs):
    tfidf = {}
    for word,val in tfBow.items():
        tfidf[word]=val * idfs[word]
    return tfidf
    

url = ('https://newsapi.org/v2/top-headlines?'
       'country=us&'
       'apiKey=0c1beeb22fb0407aaa68d7c35e21dff1')
response = requests.get(url)
json_data = json.loads(response.text)
wordset = set()
doc = []

for i in json_data["articles"]:
    a=str(i["description"]) 
    word_tokens = word_tokenize(a)
    filtered_sentence = [w for w in word_tokens if not w in stop_words]
    filtered_sentence = [e for e in filtered_sentence if e.isalnum()]
    #b=text.split(" ")
    doc+=[filtered_sentence,]
    wordset=wordset.union(set(filtered_sentence))
voab = {}
    
tfl=[]
docdoc = []
#print("doc:")
#print(doc)
#print(wordset)
for bow in doc:
    wordDict =dict.fromkeys(wordset, 0)
    for word in bow:
        wordDict[word]+=1
    docdoc += [wordDict,]    
    #print("wordDict : ")
    #print(wordDict)
    tfBow = computeTF(wordDict,bow)
    tfl+=[tfBow,]
    #print(bow)
    #print(tfBow)
    print("\n")

#print("docdoc :")
#print(docdoc)
idfs = computeIDF(docdoc)
#print(idfs)
print("tfl:")
print(tfl)

tfidfl =[]
for i in tfl:
    tfid = computeTFIDF(i,idfs)
    print("tf-idf:")
    sorted_list=[k for v,k in sorted([(v,k) for k,v in tfid.items()],reverse=True)]
    print(sorted_list[:10])
    strtfid = ','.join(sorted_list[:10])
    tfidfl += [strtfid,]
    #print("\n")

#print(tfidfl)
#import pandas as pd
#print(pd.DataFrame(tfidfl))

connection = pyodbc.connect(
r'DRIVER={SQL Server Native Client 11.0};'
r'SERVER=M-1736;'
r'DATABASE=NewsLetterApplication;'
"Trusted_Connection=yes;"
)

counter=0;
for i in json_data["articles"]:
    a=str(i["description"])
    b=str(i['urlToImage'])
    c=str(i["title"])
    d=str(i["url"])
    cursor = connection.cursor()   
    #SQL Query
    SQLCommand = ("INSERT INTO News(headline, description, imageurl, url,newsprofile) VALUES (?,?,?,?,?)")
    #SQLCommand = ("INSERT INTO News(headline, description, imageurl, url) VALUES (c,a,b,d)")
    #SQLCommand = ("INSERT INTO EmployeeMaster(Name, Salary, Mobile, Designation) VALUES (?,?,?,?)")  
    #Processing Query  
    #cursor.execute(SQLCommand)
    j=tfidfl[counter]
    counter=counter+1
    Values = [c,a,b,d,j]    
    cursor.execute(SQLCommand,Values) 
    #Commiting any pending transaction to the database.  
    connection.commit()  
    #closing connection  
    
connection.close()  
print("Data Successfully Inserted")


