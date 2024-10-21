# Smart system Lighting system parameter source 

nom_du_paramètre = VALUE # unité # source 

```
systemLifetime = 5 # years
activityPercentage = 0.33 # 8 hours a day 
timeGainPercentage = 0.3  # Estimation 
electricityCarbonIntensity = 53 # g CO2e/KWH # Electricity map France sur 12 mois https://app.electricitymaps.com/map

sensor.capteur_d_industrie.GHG_embodied = 45.9  # MJ  # NegaOctet v1.5; indicateur sur 1 année ramené à 3 ans (indicateur*3)
sensor.capteur_d_industrie.GHG_embodied = 3.75  # kg CO2e # NegaOctet v1.5; indicateur sur 1 année ramené à 3 ans (indicateur*3)
sensor.capteur_d_industrie.lifetime = 3 # years # NegaOctet v1.5

hub.sterm.powerActive = 1.25 # W # STERM 
hub.sterm.GHG_embodied = 39.21 # MJ #STERM 
hub.sterm.lifetime = 4 # years  # (Extrapolated from Google hubs)

hub.google_home.powerActive = 3.1 # W # Spécifications google hub 
hub.google_home.GHG_embodied = 34.2 # kg CO2e # Boavizta https://www.gstatic.com/gumdrop/sustainability/googlehomehub-productenvironmentalreport.pdf

item.bulb.powerActive = 9 # W # https://www.philips-hue.com/fr-fr/p/hue-white-ambiance-a60---ampoule-connectee-e27---800/8719514328167#specifications
item.bulb.powerSleep = 0.5 # W # https://www.philips-hue.com/fr-fr/p/hue-white-ambiance-a60---ampoule-connectee-e27---800/8719514328167#specifications
item.bulb.GHG_embodied = 11.94 # MJ # Annexe-STERM-UseCase-param, Gabi Electonics http://www.gabi-software.com/support/gabi/gabi-database-2020-lci-documentation/extension-database-xi-electronics/
	
item.bulb.GHG_embodied = 3 # kg CO2e # Hue https://www.philips-hue.com/fr-fr/p/hue-white-ambiance-a60---ampoule-connectee-e27---800/8719514328167#specifications  http://lib.tkk.fi/Diss/2013/isbn9789526052502/isbn9789526052502.pdf Life cycle assessment of lightsources –Casestudies and review of the analyses LeenaTähkämö  DOCTORAL DISSERTATIONS 2013
item.bulb.lifetime = 8 years

battery.AAA.GHG_embodied = 0.324 # MJ  #  max 1.8Wh for alkaline AAA battery 
"Il faut 50 fois plus d’énergie pour fabriquer une pile alcaline que ce qu’elle fournira pendant toute sa durée de vie." https://www.consoglobe.com/piles-alcalines-jetables-vs-piles-rechargeables-2787-cg (max 1.8Wh for alkaline AAA battery)
battery.AAA.lifetime = 1 # year
battery.AAA.GHG_embodied = 0.065 # kg eq CO2 # base empreinte de l'ADEME
```

## Base Boavista 
- Données constructeur  https://github.com/Boavizta/environmental-footprint-data/blob/main/boavizta-data-fr.csv
- readme https://github.com/Boavizta/environmental-footprint-data/blob/main/README.md

gwp_total: GHG emissions (estimated as CO2 equivalent, the unit is kgCO2eq) through the total lifecycle of the product (Manufacturing, Transportation, Use phase and Recycling)
the use phase

gwp_use_ratio: part of the GHG emissions coming from the use phase (the hypothesis for this use phase are detailed in the other columns, especially the lifetime and the use_location)

il convient de prendre
gwp_embodied=gwp-total*(1-gwp_use_ratio)

## Base ADEME 
https://base-empreinte.ademe.fr/
