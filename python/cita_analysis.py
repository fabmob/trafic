# -*- coding: utf-8 -*-
"""
Created on Wed Aug  5 08:52:19 2020

@author: YannCLAUDEL
"""

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import pickle
import math
from matplotlib import dates
from datetime import datetime
import networkx as nx
plt.style.use('seaborn')

################################################################################################
# Global variables / Generic functions
################################################################################################

lux = (49.61167 , 6.13)
files=['data/datexDataA1.csv','data/datexDataA3.csv','data/datexDataA4.csv','data/datexDataA6.csv','data/datexDataA7.csv','data/datexDataA13.csv','data/datexDataB40.csv']
highway = ['A1', 'A3', 'A4', 'A6', 'A7', 'A13', 'B40']
urls ={'A1': "https://www.cita.lu/info_trafic/datex/trafficstatus_a1",
        'A3':"https://www.cita.lu/info_trafic/datex/trafficstatus_a3",
        'A4':"https://www.cita.lu/info_trafic/datex/trafficstatus_a4",
        'A6':"https://www.cita.lu/info_trafic/datex/trafficstatus_a6",
        'A7':"https://www.cita.lu/info_trafic/datex/trafficstatus_a7",
        'A13':"https://www.cita.lu/info_trafic/datex/trafficstatus_a13",
        'B40':"https://www.cita.lu/info_trafic/datex/trafficstatus_b40"}

file_datex_7j = "./object/datex_7j"
file_datex = 'object/datex'
file_camera = 'object/camera'

# Load and Save object
def save(name,obj):
    savefile = open(name, 'wb') 
    pickle.dump(obj, savefile)                      
    savefile.close() 

def load(name):
    savefile = open(name, 'rb')      
    return pickle.load(savefile) 

# filter data     
def getFilteredData(data,idCamera,indexMin='2000-01-01 00:00:00+0000',indexMax='2030-01-01 00:00:00+0000'):
    result = data[(data.index>indexMin) ]
    result = result[(result.index<indexMax)]
    result = result[(result['id'] == idCamera)].fillna(method = 'ffill')
    return result


def getFilteredDataByHighway(data,road,direction='outboundFromTown',indexMin='2000-01-01 00:00:00+0000',indexMax='2030-01-01 00:00:00+0000'):
    result = data[(data.index>indexMin) ]
    result = result[(result.index<indexMax)]
    result = result[(result['direction'] == direction)]
    result = result[(result['road'] == road)].fillna(method = 'ffill')
    return result


def plot_long_serie(data,title='',label='',xlabel='Time',ylabel='',file='temp.jpg', dpi=200):
    days = dates.DayLocator()
    dfmt = dates.DateFormatter('%b %d')
    fig, ax = plt.subplots(figsize=(16,9), dpi=dpi)  
    ax.set_title(title)
    ax.plot(data.index.values, data.values, label=label,linewidth=1)
    ax.set_xlabel(xlabel)
    ax.set_ylabel(ylabel)
    ax.legend()
    ax.xaxis.set_major_locator(days)
    ax.xaxis.set_major_formatter(dfmt)
    ax.xaxis.set_tick_params(which='major',labelsize=7)
    ax.grid(True)
    fig.savefig(file)
    # plt.show()


def plot_day_traffic(data0,data1,titles,file,dpi=200):    
    fig, axes = plt.subplots(2, 7, figsize=(20,10),dpi=dpi)    
    yLimite=data0.max()*1.1
    for i, a in zip(range(7), axes[0].ravel()):
        df = data0[data0.index.dayofweek==i]
        df.index = [df.index.time,df.index.date]
        df = df.unstack().interpolate()
        for column in df:
            a.plot(df.index.map(lambda x: (x.minute+x.hour*60)/60 ), df[column], marker='', linewidth=1, alpha=0.9) 
            a.set_ylim(0,yLimite)
            a.set_title(titles[i])
            a.set_xticks(np.arange(0,25,3))
    yLimite=data1.max()*1.1             
    for i, a in zip(range(7), axes[1].ravel()):
        df = data1[data1.index.dayofweek==i]
        df.index = [df.index.time,df.index.date]
        df = df.unstack().interpolate()
        for column in df:
            a.plot(df.index.map(lambda x: (x.minute+x.hour*60)/60 ), df[column], marker='', linewidth=1, alpha=0.9)                     
            a.set_ylim(0,yLimite)
            a.set_xticks(np.arange(0,25,3))
    fig.savefig(file)
    # plt.show()

# Draw multiple plots 
def plot_mult(datas,labels,xlabel,ylabel,file, dpi=200):
    days = dates.DayLocator()
    hours = dates.HourLocator(byhour=[0,6,12,18])
    dfmt = dates.DateFormatter('%b %d')
    fig, ax = plt.subplots(figsize=(16,9), dpi=dpi)  
    
    for i, data in enumerate(datas):
        plabel = labels[i]
        ax.plot(data.index.values, data.values, label=plabel,linewidth=1)
    ax.set_xlabel(xlabel)
    ax.set_ylabel(ylabel)
    ax.legend()
    ax.xaxis.set_major_locator(days)
    ax.xaxis.set_major_formatter(dfmt)
    ax.xaxis.set_minor_locator(hours)
    ax.xaxis.set_minor_formatter(dates.DateFormatter('%H'))
    ax.xaxis.set_tick_params(which='major', pad=15,labelsize=7)
    ax.xaxis.set_tick_params(which='minor', labelsize=4)
    ax.grid(True)
    fig.savefig(file)
    
# Get Previous Camera Dict
def getPreviousCamDict(camera):
    prev_cam = {}
    for road in camera['road'].unique():
        fromLux = camera.loc[(camera['road']==road)&(camera['direction']=='outboundFromTown')].copy()
        toLux = camera.loc[(camera['road']==road)&(camera['direction']=='inboundTowardsTown')].copy()
        fromLux.sort_values(by=['direction_dist'],inplace=True,ascending = True)
        toLux.sort_values(by=['direction_dist'],inplace=True,ascending = False)
        
        toLux['previd']=toLux['id'].shift(1)
        toLux.dropna(inplace=True)
        fromLux['previd']=fromLux['id'].shift(1)
        fromLux.dropna(inplace=True)
        
        for lux in [fromLux,toLux]:
            if lux.shape[0]>0:
                for index, row in lux.iterrows():
                    prev_cam[row['id']] = row['previd']  
    return prev_cam    

# Graph

def getGraph(cameras,direction = "outboundFromTown"):
    G = nx.DiGraph(label="TRAFFIC")
    dict_previous_cam = getPreviousCamDict(cameras)
    for i, rowi in cameras.loc[cameras["direction"]==direction].iterrows():
        G.add_node(rowi['id'],key=rowi['id'],label="id",road=rowi['road'],latitude=rowi['latitude'],longitude=rowi['longitude'])
        previous = dict_previous_cam.get(rowi['id'])
        if previous != None:
            G.add_edge(previous,rowi['id'])            
    return G

def printMap(G,file='temp.jpg',title=""):
    colors=[]
    pos={}
    pos2={}
    labels={}
    dict_colors = {'A1':'orange', 'A3':'blue', 'A4':'red', 'A6':'green', 'A7':'black', 'A13':'yellow', 'B40':'cyan'}    
    fig, ax = plt.subplots(figsize=(16,16), dpi=200)  
    

    # graph    
    for e in G:
        pos[e]=(G.nodes[e]['longitude'],G.nodes[e]['latitude'])
        pos2[e]=(G.nodes[e]['longitude']+0.010,G.nodes[e]['latitude']+0.001)
        new = e.split(".")
        labels[e] = new[2]
        colors.append(dict_colors.get(G.nodes[e]['road']))
    
    # nodes
    nx.draw_networkx_nodes(G, pos,node_size=50,node_color=colors)
    
    nx.draw_networkx_edges(G, pos, node_size=50, arrowstyle='->',arrowsize=20)
    # labels
    nx.draw_networkx_labels(G, pos2, labels= labels,font_size=10)
    
    markers = [plt.Line2D([0,0],[0,0],color=color, marker='o', linestyle='') for color in dict_colors.values()]
    plt.legend(markers, dict_colors.keys(), numpoints=1,loc='upper left') 
    
    # draw bounderies on map
    plt.title(title)
    fig.savefig(file)
    #plt.show()
    
    
################################################################################################
# Read file and build dataframe
################################################################################################

frames=[]
for file in files:
    print(file + ' start')
    df = pd.read_csv(file, parse_dates = False, header = None,sep=';')
    df.columns=['id','time','latitude','longitude','direction','road','trafficStatus','avgVehicleSpeed','vehicleFlowRate','trafficConcentration']
    df.loc[df['time'].str.len()<25,'time']=pd.NaT
    df['time'] = pd.to_datetime(df['time'],errors='coerce',utc=False)  
    df['time'] = df['time'].fillna(method = 'ffill')
    #df.index = df['time','id']
    df.set_index(['time','id'],drop=False,inplace=True)
    df=df[~df.index.duplicated()]
    df.set_index(['time'],drop=False,inplace=True)
    df['dayofweek'] = df.index.dayofweek
    df['day'] = df.index.day
    df['hour'] = df.index.hour
    new = df["id"].str.split(".", expand = True) 
    df['highway']=new[0]
    df['direction_code']=new[1]
    df['direction_dist']=pd.to_numeric(new[2])    
    # df=df[~camera.index.duplicated()]
    frames.append(df)
    print(file + ' end')
data = pd.concat(frames)

data.dropna(inplace=True)
# Build camera dataframe
camera = data[['id','latitude','longitude','direction_dist','direction_code','road','direction']]
camera.index = camera['id']
camera=camera[~camera.index.duplicated()]
save(file_camera,camera)
save(file_datex,data)
del(new,frames,file,df)

################################################################################################
# Explore data
################################################################################################

road = 'A3'
  
fromLuxCam = 'A3.VM.11397'
toLuxCam = 'A3.MV.11397'
fromDate = '2019-11-25 00:00:00+0000'
toDate = '2019-12-23 00:00:00+0000'
print("min index is",data.index.min())
print("max index is",data.index.max())

GFrom = getGraph(camera,direction="outboundFromTown")
GTo = getGraph(camera,direction="inboundTowardsTown")

printMap(GTo,file='out/lucCamTo.png',title="Cameras - direction = to Luxembourg")
printMap(GFrom,file='out/lucCamFrom.png',title="Cameras - direction = From Luxembourg")

fromLux = getFilteredData(data,fromLuxCam,fromDate,toDate)
toLux = getFilteredData(data,toLuxCam,fromDate,toDate)

fromLux_rolling_avg = fromLux.avgVehicleSpeed.rolling(window=10,center=True).mean()
fromLux_rolling_flow = fromLux.vehicleFlowRate.rolling(window=10,center=True).mean()
fromLux_rolling_traf = fromLux.trafficConcentration.rolling(window=10,center=True).mean()
fromLux_rolling_avg.dropna(inplace=True)
fromLux_rolling_flow.dropna(inplace=True)
fromLux_rolling_traf.dropna(inplace=True)

toLux_rolling_avg = toLux.avgVehicleSpeed.rolling(window=10,center=True).mean()
toLux_rolling_flow = toLux.vehicleFlowRate.rolling(window=10,center=True).mean()
toLux_rolling_traf = toLux.trafficConcentration.rolling(window=10,center=True).mean()
toLux_rolling_avg.dropna(inplace=True)
toLux_rolling_flow.dropna(inplace=True)
toLux_rolling_traf.dropna(inplace=True)


print("min index is",fromLux.index.min())
print("max index is",fromLux.index.max())


plot_long_serie(fromLux_rolling_avg,title='From LUX camera={}'.format(fromLuxCam),label='Average Vehicle Speed',xlabel='Date',ylabel='Speed',file='out/{}_avgVehicleSpeed.png'.format(fromLuxCam))
plot_long_serie(fromLux_rolling_flow,title='From LUX camera={}'.format(fromLuxCam),label='Vehicle Flow Rate',xlabel='Date',ylabel='Rate',file='out/{}_vehicleFlowRate.png'.format(fromLuxCam))
plot_long_serie(fromLux_rolling_traf,title='From LUX camera={}'.format(fromLuxCam),label='Traffic Concentration',xlabel='Date',ylabel='Concentration',file='out/{}_trafficConcentration.png'.format(fromLuxCam))

plot_long_serie(toLux_rolling_avg,title='To LUX camera={}'.format(toLuxCam),label='Average Vehicle Speed',xlabel='Date',ylabel='Speed',file='out/{}_avgVehicleSpeed.png'.format(toLuxCam))
plot_long_serie(toLux_rolling_flow,title='To LUX camera={}'.format(toLuxCam),label='Vehicle Flow Rate',xlabel='Date',ylabel='Rate',file='out/{}_vehicleFlowRate.png'.format(toLuxCam))
plot_long_serie(toLux_rolling_traf,title='To LUX camera={}'.format(toLuxCam),label='Traffic Concentration',xlabel='Date',ylabel='Concentration',file='out/{}_trafficConcentration.png'.format(toLuxCam))

titles=['Monday','Tueday','Wednesday','Thursday','Friday','Saturday','Sunday']

plot_day_traffic(toLux_rolling_avg,fromLux_rolling_avg,titles,"out/{}_day_traffic_avg_speed.jpg".format(toLuxCam))
plot_day_traffic(toLux_rolling_flow,fromLux_rolling_flow,titles,"out/{}_day_traffic_flow_rate.jpg".format(toLuxCam))
plot_day_traffic(toLux_rolling_traf,fromLux_rolling_traf,titles,"out/{}_day_traffic_concentration.jpg".format(toLuxCam))


for direction in ['outboundFromTown','inboundTowardsTown']:
    df = getFilteredDataByHighway(data,road,direction,fromDate,toDate)
    cams = camera.loc[(camera['road']==road)&(camera['direction']==direction)].sort_values(by=['direction_dist'])['id'].values
    datas=[]
    datas2=[]
    datas3=[]
    labels=[]
    for cam in cams:    
        temp=df.loc[(df['id']==cam)&(df.index.dayofweek.isin([0,1,2,3,4]))].copy()
        temp['minute']=temp.index.time
        temp.avgVehicleSpeed = temp.avgVehicleSpeed.rolling(window=3,center=True).mean()
        temp.vehicleFlowRate = temp.vehicleFlowRate.rolling(window=3,center=True).mean()
        temp.trafficConcentration = temp.trafficConcentration.rolling(window=3,center=True).mean()
        temp.dropna(inplace=True)
        data1 = temp.groupby('minute').mean()["avgVehicleSpeed"].copy()
        data1.index = data1.index.map(lambda x : x.strftime("%H:%M:%S"))
        datas.append(data1)
        data2 = temp.groupby('minute').mean()["vehicleFlowRate"].copy()
        data2.index = data2.index.map(lambda x : x.strftime("%H:%M:%S"))
        datas2.append(data2)
        data3 = temp.groupby('minute').mean()["trafficConcentration"].copy()
        data3.index = data3.index.map(lambda x : x.strftime("%H:%M:%S"))
        datas3.append(data3)
        labels.append(cam)
        
    plot_mult(datas,labels,'Date','avgVehicleSpeed',file='out/{}_{}_avgVehicleSpeed.png'.format(road,direction))    
    plot_mult(datas2,labels,'Date','vehicleFlowRate',file='out/{}_{}_vehicleFlowRate.png'.format(road,direction))     
    plot_mult(datas3,labels,'Date','vehicleFlowRate',file='out/{}_{}_trafficConcentration.png'.format(road,direction)) 


toLux = getFilteredData(data,toLuxCam)
toLux.loc[toLux['dayofweek']<5].groupby(['hour']).mean()['avgVehicleSpeed']

######################################################
## First Model - Random forest
######################################################


from sklearn.metrics import confusion_matrix
from sklearn.metrics import accuracy_score,precision_score, recall_score,f1_score,SCORERS
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import GridSearchCV
from sklearn.model_selection import train_test_split
from timeit import default_timer as timer
from sklearn.preprocessing import MinMaxScaler


######################################################
## Generate X and y
######################################################
def generateDf(dataIn,cam,cam1):
    df0 = getFilteredData(dataIn,cam)
    df1 = getFilteredData(dataIn,cam1)
    df1 = df1[['avgVehicleSpeed', 'vehicleFlowRate']]
    col_rename = {}
    for col in df1.columns:
        col_rename[col]='prev_station_' + col
    
    df1.rename(columns=col_rename,inplace=True)
    df = df0.join(df1)
    df=df[['avgVehicleSpeed', 'vehicleFlowRate','trafficConcentration','dayofweek','hour','prev_station_avgVehicleSpeed', 'prev_station_vehicleFlowRate']].copy()
    df['isWeekend'] = df['dayofweek'].map(lambda x : 0 if x < 5 else 1)

    # Diff %
    for i in range(1,backward+1):
         df['avgDiff'+str(i)] = df['avgVehicleSpeed'].shift(i-1)/ df['avgVehicleSpeed'].shift(i) - 1
         df['avgDiff'+str(i)].replace([np.inf, -np.inf], np.nan,inplace=True)
         df['avgDiff'+str(i)].fillna(method='bfill')
         df['flowDiff'+str(i)] = df['vehicleFlowRate'].shift(i-1)/ df['vehicleFlowRate'].shift(i) - 1
         df['flowDiff'+str(i)].replace([np.inf, -np.inf], np.nan,inplace=True)
         df['flowDiff'+str(i)].fillna(method='bfill')
         df['flowTraffic'+str(i)] = df['trafficConcentration'].shift(i-1)/ df['trafficConcentration'].shift(i) - 1
         df['flowTraffic'+str(i)].replace([np.inf, -np.inf], np.nan,inplace=True)
         df['flowTraffic'+str(i)].fillna(method='bfill')
         
    # EWL
    df['EWMavg']=df['avgVehicleSpeed'].ewm(span=3, adjust=False).mean()
    df['EWMflow']=df['vehicleFlowRate'].ewm(span=3, adjust=False).mean()
    df['EWMtraffic']=df['trafficConcentration'].ewm(span=3, adjust=False).mean()
    return df

def generateXYspeed20(df):    
    df['ydiff'] = df['avgVehicleSpeed'].shift(forward)/df['avgVehicleSpeed'] - 1    
    df['y'] = 0
    df.loc[df['ydiff']<-0.2,['y']]=1
    df.dropna(inplace=True)
    y = df['y']
    X = df.drop(['y','ydiff'], axis=1)
    return X , y

def generateXYspeedUnder(df):    
    mean = df['avgVehicleSpeed'].mean()
    df['ydiff'] = df['avgVehicleSpeed'].shift(forward)
    df['y'] = 0
    df.loc[df['ydiff']<mean*0.6,['y']]=1
    df.dropna(inplace=True)
    y = df['y']
    X = df.drop(['y','ydiff'], axis=1)
    return X , y

def generateXYspeedAndFlowUnder(df):    
    means = df['avgVehicleSpeed'].mean()
    meanf = df['vehicleFlowRate'].mean()
    df['ydiffSpeed'] = df['avgVehicleSpeed'].shift(forward)
    df['ydiffFlow'] = df['vehicleFlowRate'].shift(forward)
    df['y'] = 0
    df.loc[(df['ydiffSpeed']<means*0.6) &(df['ydiffFlow']<meanf*0.6),['y']]=1
    df.dropna(inplace=True)
    y = df['y']
    X = df.drop(['y','ydiffSpeed','ydiffFlow'], axis=1)
    return X , y

def print_metrics(y_true,y_pred):
    conf_mx = confusion_matrix(y_true,y_pred)
    print(conf_mx)
    print (" Accuracy    : ", accuracy_score(y_true,y_pred))
    print (" Precision   : ", precision_score(y_true,y_pred))
    print (" Sensitivity : ", recall_score(y_true,y_pred))


def train_model(X,y):
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.20, random_state=42)
    start = timer()
    forest = RandomForestClassifier(max_depth = 10, n_estimators = 500, random_state = 42)
    random_forest = forest.fit(X_train,y_train)
    end = timer()
    
    y_pred = random_forest.predict(X_train)
    print ("------------------------------------------")
    print ("TRAIN")
    print_metrics(y_train,y_pred)
    importances = list(zip(random_forest.feature_importances_, X.columns))
    importances.sort(reverse=True)
    print([x for (_,x) in importances[0:5]])
    y_pred = random_forest.predict(X_test)
    print ("------------------------------------------")
    print ("TEST")
    print_metrics(y_test,y_pred)
    
    return random_forest 
        
cam = 'A3.MV.10437'
cam1= 'A3.MV.11397'   

forward = -3
backward = 3    
df = generateDf(data,cam,cam1)


print ('camera :',cam)
print ("---------------------------------------------------------------")
print ("Predict 20% speed drop")
X,y = generateXYspeed20(df)
model = train_model(X,y)

print ("---------------------------------------------------------------")
print ("Predict speed less than 60% of the average speed")
X,y = generateXYspeedUnder(df)
model = train_model(X,y)

print ("---------------------------------------------------------------")
print ("Predict speed anf flow less than 60% of the average")
X,y = generateXYspeedAndFlowUnder(df)
model = train_model(X,y)
