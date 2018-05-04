import requests
import json
import pyodbc
import re
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
stop_words = set(stopwords.words('english'))
import os
from stat import *
import requests
from bs4 import BeautifulSoup
from textblob import TextBlob


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
       try:
          tfDict[word]=count/float(bowcount)
       except :
          pass
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



wordset = set()
doc = []
    


#fetching NEWSAPI news

url = ('https://newsapi.org/v2/top-headlines?'
       'country=us&'
       'apiKey=1970b1f7f9ca43cfaf3f8205c79e52c7')
response = requests.get(url)
json_data = json.loads(response.text)






#print("NewsAPI news:")
#print(json_data)







#fetching scrapped news

scrapurl=[]
base_url=" https://timesofindia.indiatimes.com/india"
scrapurl=[" https://timesofindia.indiatimes.com/india"]
for i in range(1,5,1):
    scrapurl.append(base_url+'/'+str(i))   
correct_soup=[]
for i in scrapurl:
    res=requests.get(i)
    soup=BeautifulSoup(res.content, 'html.parser')
    correct_soup.append(soup)
correct_div=[]
for i in range(len(correct_soup)):
    correct_div=(soup.find('div',{'id':'c_02010501'}).find('div',{'id':'c_wdt_list_1'}).find('ul',{'class':'list5 clearfix'}).find_all('li'))
headlines=[]
urls=[]
l=[]
image_url=[]
base_url="https://timesofindia.indiatimes.com/india"
for i in correct_div:
    headlines=i.find('span',{'class':'w_tle'}).find('a')['title']
    url=i.find('span',{'class':'w_tle'}).find('a')['href']   
    res=base_url+url
    res_two=requests.get(res)
    soup=BeautifulSoup(res_two.content, 'html.parser')
    description=soup.find('div',{'class':'Normal'}).get_text().replace("\n"," ")
    image_url="https://is3-ssl.mzstatic.com/image/thumb/Purple118/v4/e8/1d/90/e81d90a0-773b-2f26-fa10-295af720ee40/AppIcon-1x_U007emarketing-85-220-0-7.png/246x0w.jpg"   
    l.append((headlines,res,description,image_url))



#print("scrapped news:")
#print(l)







#for NewsAPI
for i in json_data["articles"]:
    a=str(i["description"])
    is_noun = lambda pos: pos[:2] == 'NN'
    word_tokens = word_tokenize(a)
    nouns = [word for (word, pos) in nltk.pos_tag(word_tokens) if is_noun(pos)] 
    #print(type(word_tokens))
    #blob = TextBlob(a)
    #b=blob.noun_phrases
    #print(type(b))
    #print(blob)
    filtered_sentence = [w for w in nouns if not w in stop_words]
    filtered_sentence = [e for e in filtered_sentence if e.isalnum()]
    doc+=[filtered_sentence,]
    wordset=wordset.union(set(filtered_sentence))



#for Scrapped news
for i in l:
    a=i[2]
    is_noun = lambda pos: pos[:2] == 'NN'
    word_tokens = word_tokenize(a)
    nouns = [word for (word, pos) in nltk.pos_tag(word_tokens) if is_noun(pos)] 
    #blob = TextBlob(a)
    #b=blob.noun_phrases
    #print(blob)
    filtered_sentence = [w for w in nouns if not w in stop_words]
    filtered_sentence = [e for e in filtered_sentence if e.isalnum()]
    doc+=[filtered_sentence,]
    wordset=wordset.union(set(filtered_sentence))











    
voab = {}    
tfl=[]
docdoc = []



#print("doc:")
#print(doc)
#print("wordset:")
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
    #print("\n")

#print("docdoc :")
#print(docdoc)
idfs = computeIDF(docdoc)
#print(idfs)
#print("tfl:")
#print(tfl)

tfidfl =[]
for i in tfl:
    tfid = computeTFIDF(i,idfs)
    #print("tf-idf:")
    sorted_list=[k for v,k in sorted([(v,k) for k,v in tfid.items()],reverse=True)]
    #print(sorted_list[:10])
    strtfid = ','.join(sorted_list[:10])
    tfidfl += [strtfid,]
    #print("\n")


connection = pyodbc.connect(
r'DRIVER={SQL Server Native Client 11.0};'
r'SERVER=M-1736\MSSQL;'
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
    SQLCommand = ("INSERT INTO News(headline, description, imageurl, url,newsprofile) VALUES (?,?,?,?,?)")
    j=tfidfl[counter]
    counter=counter+1
    Values = [c,a,b,d,j]    
    cursor.execute(SQLCommand,Values)
    connection.commit()

for i in l:
    a=str(i[2])
    b=str(i[3])
    c=str(i[0])
    d=str(i[1])
    cursor = connection.cursor()
    SQLCommand = ("INSERT INTO News(headline, description, imageurl, url,newsprofile) VALUES (?,?,?,?,?)")
    j=tfidfl[counter]
    counter=counter+1
    Values = [c,a,b,d,j]    
    cursor.execute(SQLCommand,Values)
    connection.commit()

    
    
connection.close()  
print("Data Successfully Inserted")


