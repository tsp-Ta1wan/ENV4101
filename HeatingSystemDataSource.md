# Smart system Heating system parameter source 

# unité # source 
nom_du_paramètre = VALUE 

be careful to write a property file 
no space between two values e.g. sensors = capteur_d_industrie,thermostat

```
# *** System Data ***
# Lifetime # years
systemLifetime = 5 
# # in the interval [0,1] # Depends on the country ... for France 6 heures par jour pendant 150 jours par an # https://www.edfenr.com/guide-solaire/consommation-moyenne-chauffage-electrique/ 
activityPercentage = xxxx
# Estimation in the interval [0,1] # #for example read here https://librairie.ademe.fr/urbanisme/5792-pourquoi-passer-au-thermostat-programmable-.html 
timeGainPercentage = xxxx  
# g CO2e/KWH # Electricity map  onglet consommation et données annuel https://app.electricitymaps.com/map
electricityCarbonIntensity = xxxx 

# *** Sensors Data ***
# MJ  # NegaOctet v1.5; column "PEF-GWP (kg CO2 eq.)" gives a value for 1 year, You have to multiply by the value of column "Durée de vie de l'équipement" # 
sensor.capteur_d_industrie.GHG_embodied = xxxx  
# years # NegaOctet v1.5 column "Durée de vie de l'équipement"
sensor.capteur_d_industrie.lifetime = xxxx 
# Kg CO2e # NegaOctet v1.5 column "PEF-GWP (kg CO2 eq.)" gives a value for 1 year, You have to multiply by the value of column "Durée de vie de l'équipement" # 
sensor.thermostat.GHG_embodied = xxxx
# years # NegaOctet v1.5 column "Durée de vie de l'équipement"
sensor.thermostat.lifetime = xxxx 

# *** Hub Data ***
# W # Specifications google hub 
hub.google_home.powerActive = 3.1 
# kg CO2e # Boavizta https://www.gstatic.com/gumdrop/sustainability/googlehomehub -productenvironmentalreport.pdf
hub.google_home.GHG_embodied = xxxx 

# *** Heater Data ***
# Heater Power # W # depends on the heater 
item.heater.powerActive = 1000
# Heater Power sleep # W # 0 
item.heater.powerSleep = 0
# The heater is not included in the smart system : you do not have to buy new heaters, you just have to buy sensors and hubs. As a consequence, you can consider the heater embodied emission as null **for this use case**.
item.heater.GHG_embodied = xxxx

# *** Battery Data ***
# year
battery.AAA.lifetime = 1 
# kg eq CO2 # base empreinte de l'ADEME
battery.AAA.GHG_embodied = xxxx
```

## Base Boavizta 
- Données constructeur  https://github.com/Boavizta/environmental-footprint-data/blob/main/boavizta-data-fr.csv
- readme https://github.com/Boavizta/environmental-footprint-data/blob/main/README.md

gwp_total: GHG emissions (estimated as CO2 equivalent, the unit is kgCO2eq) through the total lifecycle of the product (Manufacturing, Transportation, Use phase and Recycling)
the use phase

gwp_use_ratio: part of the GHG emissions coming from the use phase (the hypothesis for this use phase are detailed in the other columns, especially the lifetime and the use_location)

il convient de prendre
gwp_embodied=gwp-total*(1-gwp_use_ratio)

## Base ADEME 
https://base-empreinte.ademe.fr/

## Base negaoctet (public subset)
https://base-empreinte.ademe.fr/documentation/base-impact?idDocument=167

Les émissions embodied sont données sur 1 an (multiplier par la durée de vie de l'appareil)