# SmartStudy Code Structure

This code is a Java version of the STERM model proposed by the Shift Project. The purpose of this model is to compute the payback time for a smart system, to determine the extent to which the smart layer is relevant either in terms of energy or of GHG emissions. 

A smart system is made of : items (such as heaters or bulbs), hubs, sensors and batteries. 

This Java implementation enables easy definition of new systems through the creation of one property file by system.

To analyse the smart system two graphs are plotted : 
- paybackTime by timeGainPercentage 
- paybackTime by nbItems

## Installs required
In order to run the model, you need :
- JDK version >= 17
- Maven version >= 3.8.6
- Python 3 (for the graphs, use of matplotlib4j)
  - Python packages :
	+ Numpy 
	+ Spicy
	+ Tqdm

To run the model, execute the following commands in this folder :

```
mvn clean install
java -jar target/LCA_IoT-1.0-SNAPSHOT.jar SystemName.properties
```
where `SystemName.properties` is the property file for the system you want.

If you want to see the project in an IDE choose "import existing maven project". 

## Global logic

![Class diagram](diagramme_de_classe/diagramme_SmartStudy.png "Class diagram")

A smart system is a connected system, such as an autonomous lighting system based on presence detection. This type of system makes it possible to save time of use, and therefore energy. However, this smart layer needs energy to operate, and energy due to the life cycle of the new material required (hubs, presence sensors). The aim of this logiciel is to compute the payback time of a system, to determine the number of years of use of the smart system needed to be worthwhile in terms of energy compared to a non-connected one.

In this model, a `ConsumingSystem` consists of 4 different components : 
- `Items` that are at the heart of the smart system (e.g. heaters, bulbs) the elements on which you should make savings
- `Hubs`, to connect the smart layer
- `Sensors`, to make this smart layer autonomous (such as presence sensors)
- `Batteries`, to power the sensors.

### Calculation of the payback time
The calculation of the payback time is the ratio between the cost of implementation of the smart system and of the energy it saves each year. 
For example, if the total embodied energy of all your materials is 100MJ, but you save 20MJ of raw energy each year you use the system, the payback time is 100 / 20 = 5 years.
It works the same way with g eq CO2, this calculation is only a matter of balancing the cost against the energy saved.

The calculation of the cost of implementation is only the sum of the embodied cost of all the materials necessary to implement the smart system (hub, sensors, items, batteries)

The calculation of the energy saved due to the smart system each year is :
- The electrical energy saved due to the smart layer. The model simply considers that due to the smart layer, you save timeGainPercentage percentage of time of utilisation, and so you can calculate the electrical energy saved. This electrical energy is converted in the same unity as the embodied cost of materials (kg eq CO2 or J or raw energy). This convertion factor is either the energy mix of the country where you use the system or a factor of 3, presented by the STERM project as the factor to convert eletric energy in raw energy.
- Subtracted by the cost of the smart layer : 
	- The electrical energy it consumes to work (for hubs, and sleep mode of items). This electrical energy is converted with the same factor as before.
	- The embodied energy of the maintenance of the smart layer. Each material has a lifetime, and should be replaced with a new material after this time. This cost is considered by year : if a hub costs 30 kg CO2 to be produced, and have a lifetime of 5 years, it is counted in the computation as a annual cost of 30 / 5 = 6 kg eq CO2.

For more details on the mathematical model, see the [description of the STERM model](https://theshiftproject.org/wp-content/uploads/2021/04/Annexes-Analyser-la-pertinence-energetique-des-technologies-connectees.pdf)

## Detailled logic : utilisation of each property

### System level
- **SmartSystem** is the name of the system plotted on the graph.
- **systemLifetime** is the threshold line plotted on the graph
- **embodied** : *CO2* or *greyEnergy* 
  - *greyEnergy* computation in (MJ) 
  - *CO2* computation in (kg eq CO2)
- **electricityCarbonIntensity** : if the unit of embodied cost of each material is kg eq CO2, this energy mix is the factor to convert electrical energy in kg eq CO2 to correctly compare all the costs and savings in the calculation. The unit of the *electricityCarbonIntensity* parameter is **g eq CO2 / kWh**. (you can find those values here [Electricity maps](https://app.electricitymaps.com/zone/FR), take the mean for 12 month)


- **timeGainPercentage** is the percentage/100 (the coefficient between 0 and 1) of usage gained due to the smart layer. For example, if your lights are on 8h each day, and you consider that the smart layer can makes you turn the lights off during 2 more hours each day, the time gain percentage is 2/8=0.25 
- **activityPercentage** is the percentage/100 (the coefficient between 0 and 1) of time your system is on. If your lights are on 8h each day 7d/7, then the activity percentage is 8/24 = 0.33

### Description of the smart system 
Then, you have to detail all the materials used in your system. Put the name of the material, and then the number there is in your system.

- **sensors** is the name of all the kinds of sensors in your system (e.g.`sensors= presence, temperature`)
- **sensorsNb** the number of those sensors (e.g.`sensorsNb= 8, 12` if you have 8 presence sensors and 12 temperature sensors)

Then the same logic is used to describe the hubs, items and batteries :
- **hubs** 
- **hubsNb**

- **items**  items are the important elements in the smart system
- **itemsNb**

- **batteries** 
- **batteriesNb** 

### Properties of the smart system elements (sensors, hubs, items, batteries)
For each element property 
- first word is the type of element (`sensor`, `hub`, `item`, or `battery`)
- second word is the name of this element (e.g. `presence`)
- third word is the name of the property (e.g. `lifetime`)
(e.g. `sensor.capteur_d_industrie.lifetime=3`)

#### Properties common to all materials
- **GHG_embodied in kg CO2 or  MJ** is the embodied cost of the material, used in the calculations of the system's implementation cost and in the calculations of the system's maintenance cost. you provide it either in kg CO2 or in MJ according to the embodied property.
- **lifetime in years** is the time in years when you have to replace the material. It is used if the calculations of the system's maitenance cost.

#### Logic for sensors : 
- *PowerActive* and *PowerSleep* are unused, and are residual of the consumingMaterial implementation. In this system, we consider that sensors are powered with batteries. These two parameters could be used to calculate the lifetime of a battery in a further rework of the system.

#### Logic for hubs :
- **PowerActive in W** is the power permanently consumed by the hub in W, used in the calculations of the electrical energy consumed by the smart layer to work.
- *PowerSleep* is unused. Hubs should theorically enter in sleep mode sometimes to save energy, but according to measurements conducted by the Shift Project in [this annex](https://theshiftproject.org/wp-content/uploads/2021/04/Annexes-Analyser-la-pertinence-energetique-des-technologies-connectees.pdf), they never enter in sleep mode.

#### Logic for items :
- **PowerActive in W** is the power consumed by the item when in use. It is used to calculate the energy saved by the smart layer. The smart layer saves, by year : E = yearInSeconds * activityPercentage * timeGainPercentage * PowerActive. This energy is then converted by the conversion factor in kg eq CO2 or in J or raw energy.
- **PowerSleep in W** is the power permanently used by the item to be connected to hubs, and is used in the calculation of the electrical energy consumed by the smart layer to work.


## Provided property file 
For each new system you want to do the calculations, create a new `SystemName.properties` you will have to pass as an argument when you start the program. Follow the config.properties template to know what to fill in, or check the examples provided with the code. 

As well as the property file it is recommended to source the data. 
We provide 4 property files: 

```
residential-lighting-system-CO2.properties
residential-lighting-system-greyEnergy.properties
work-office-lighting-system-CO2.properties
work-office-lighting-system-greyEnergy.properties
```

And one file that explain the data source:

```
LightingSystemDataSource.md 
```

---
Author : Bernard Romain, based on the STERM model proposed by the Shift Project.
