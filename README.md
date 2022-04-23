# Cricsheet-JSON-Parser
Basic parser for ball-by-ball cricket **Test match data** available from https://cricsheet.org/downloads/

This is a barebones programme that extracts match information
from the *Cricsheet* JSON (explained in detail at: https://cricsheet.org/format/json/) and prints a match summary to the console.

This hobby project is in no way affiliated with *Cricsheet*.

Currently, this solution does **not** take into certain cricketing
edge cases, such as:
- Concussion substitutes
- Miscounted overs
- Penalty runs
- Mid-over replacement bowlers (due to injury)
- Both batters considered out on same ball (1 standard wicket + 1 retired hurt)

## How to Use
Running the LoadJSON programme prompts the user
to enter a filepath to a local JSON file.

This programme has been written with only Test matches in mind.

> Enter input filepath:
> 
> C:\Users\WFBFM\Downloads\tests_male_json\1152848.json

## Sample Output
Cast your mind back to Headingley, 2019 - where Ben Stokes put on a show
that none of us will live to forget.

```
Match #2357: England vs. Australia, at Headingley
England won by 1 wickets
--------------------------------------------------------
Match Replay: England won the toss and decided to field
--------------------------------------------------------
Australia 1st Innings: 179 - 10
----- Batter Scorecard -----
DA Warner            R: 61   B: 94   SR: 64.89  c JM Bairstow b JC Archer
MS Harris            R: 8    B: 12   SR: 66.67  c JM Bairstow b JC Archer
UT Khawaja           R: 8    B: 17   SR: 47.06  c JM Bairstow b SCJ Broad
M Labuschagne        R: 74   B: 129  SR: 57.36  lbw b BA Stokes
TM Head              R: 0    B: 6    SR: 0.00   b SCJ Broad
MS Wade              R: 0    B: 3    SR: 0.00   b JC Archer
TD Paine             R: 11   B: 26   SR: 42.31  lbw b CR Woakes
JL Pattinson         R: 2    B: 8    SR: 25.00  c JE Root b JC Archer
PJ Cummins           R: 0    B: 13   SR: 0.00   c JM Bairstow b JC Archer
NM Lyon              R: 1    B: 4    SR: 25.00  lbw b JC Archer
JR Hazlewood         R: 1    B: 3    SR: 33.33  Not Out

----- Bowler Scorecard -----
SCJ Broad            O: 14.0 M: 4  R: 32  W: 2   Econ: 2.29  Dots: 66   SR: 42.00
JC Archer            O: 17.1 M: 3  R: 45  W: 6   Econ: 2.62  Dots: 85   SR: 17.50
CR Woakes            O: 12.0 M: 4  R: 51  W: 1   Econ: 4.25  Dots: 53   SR: 73.00
BA Stokes            O: 9.0  M: 0  R: 45  W: 1   Econ: 5.00  Dots: 34   SR: 54.00

--------------------------------------------------------
England 1st Innings: 67 - 10
----- Batter Scorecard -----
RJ Burns             R: 9    B: 28   SR: 32.14  c TD Paine b PJ Cummins
JJ Roy               R: 9    B: 15   SR: 60.00  c DA Warner b JR Hazlewood
JE Root              R: 0    B: 2    SR: 0.00   c DA Warner b JR Hazlewood
JL Denly             R: 12   B: 49   SR: 24.49  c TD Paine b JL Pattinson
BA Stokes            R: 8    B: 13   SR: 61.54  c DA Warner b JL Pattinson
JM Bairstow          R: 4    B: 15   SR: 26.67  c DA Warner b JR Hazlewood
JC Buttler           R: 5    B: 16   SR: 31.25  c UT Khawaja b JR Hazlewood
CR Woakes            R: 5    B: 9    SR: 55.56  c TD Paine b PJ Cummins
JC Archer            R: 7    B: 8    SR: 87.50  c TD Paine b PJ Cummins
SCJ Broad            R: 4    B: 5    SR: 80.00  Not Out
MJ Leach             R: 1    B: 7    SR: 14.29  b JR Hazlewood

----- Bowler Scorecard -----
PJ Cummins           O: 9.0  M: 4  R: 23  W: 3   Econ: 2.56  Dots: 42   SR: 18.00
JR Hazlewood         O: 12.5 M: 2  R: 30  W: 5   Econ: 2.34  Dots: 64   SR: 15.40
NM Lyon              O: 1.0  M: 0  R: 2   W: 0   Econ: 2.00  Dots: 3    SR: 0.00
JL Pattinson         O: 5.0  M: 2  R: 9   W: 2   Econ: 1.80  Dots: 26   SR: 15.00

--------------------------------------------------------
Australia 2nd Innings: 246 - 10
----- Batter Scorecard -----
MS Harris            R: 19   B: 39   SR: 48.72  b MJ Leach
DA Warner            R: 0    B: 2    SR: 0.00   lbw b SCJ Broad
UT Khawaja           R: 23   B: 38   SR: 60.53  c JJ Roy b CR Woakes
M Labuschagne        R: 80   B: 187  SR: 42.78  run out
TM Head              R: 25   B: 56   SR: 44.64  b BA Stokes
MS Wade              R: 33   B: 59   SR: 55.93  c JM Bairstow b BA Stokes
TD Paine             R: 0    B: 2    SR: 0.00   c JL Denly b SCJ Broad
JL Pattinson         R: 20   B: 48   SR: 41.67  c JE Root b JC Archer
PJ Cummins           R: 6    B: 6    SR: 100.00 c RJ Burns b BA Stokes
NM Lyon              R: 9    B: 17   SR: 52.94  b JC Archer
JR Hazlewood         R: 4    B: 5    SR: 80.00  Not Out

----- Bowler Scorecard -----
JC Archer            O: 14.2 M: 2  R: 42  W: 2   Econ: 2.93  Dots: 65   SR: 45.50
SCJ Broad            O: 16.0 M: 2  R: 52  W: 2   Econ: 3.25  Dots: 70   SR: 48.50
CR Woakes            O: 10.0 M: 1  R: 34  W: 1   Econ: 3.40  Dots: 44   SR: 60.00
MJ Leach             O: 11.0 M: 0  R: 46  W: 1   Econ: 4.18  Dots: 42   SR: 66.00
BA Stokes            O: 24.0 M: 7  R: 54  W: 3   Econ: 2.25  Dots: 117  SR: 49.00

--------------------------------------------------------
England 2nd Innings: 362 - 9
----- Batter Scorecard -----
RJ Burns             R: 7    B: 21   SR: 33.33  c DA Warner b JR Hazlewood
JJ Roy               R: 8    B: 18   SR: 44.44  b PJ Cummins
JE Root              R: 77   B: 205  SR: 37.56  c DA Warner b NM Lyon
JL Denly             R: 50   B: 155  SR: 32.26  c TD Paine b JR Hazlewood
BA Stokes            R: 135  B: 219  SR: 61.64  Not Out
JM Bairstow          R: 36   B: 68   SR: 52.94  c M Labuschagne b JR Hazlewood
JC Buttler           R: 1    B: 9    SR: 11.11  run out
CR Woakes            R: 1    B: 8    SR: 12.50  c MS Wade b JR Hazlewood
JC Archer            R: 15   B: 33   SR: 45.45  c TM Head b NM Lyon
SCJ Broad            R: 0    B: 2    SR: 0.00   lbw b JL Pattinson
MJ Leach             R: 1    B: 17   SR: 5.88   Not Out

----- Bowler Scorecard -----
PJ Cummins           O: 24.4 M: 5  R: 80  W: 1   Econ: 3.24  Dots: 114  SR: 150.00
JR Hazlewood         O: 31.0 M: 11 R: 85  W: 4   Econ: 2.74  Dots: 145  SR: 46.75
NM Lyon              O: 39.0 M: 5  R: 114 W: 2   Econ: 2.92  Dots: 177  SR: 117.00
JL Pattinson         O: 25.0 M: 9  R: 47  W: 1   Econ: 1.88  Dots: 121  SR: 154.00
M Labuschagne        O: 6.0  M: 0  R: 16  W: 0   Econ: 2.67  Dots: 22   SR: 0.00

--------------------------------------------------------
```

Note that in the 2nd Australian innings, Ben Stokes in 
fact bowled 24.2 overs and Archer only 14.  As mentioned, the
basic programme does not currently support mid-over injury
replacements.