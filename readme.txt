Files:
1. parse.py: parses the lambda_scramble.fa file to obtain the gene sequences
2. gene sequence.txt: the result sequence after processing lambda_scramble.fa using parse.py
3. getResult.py: processes lambda_virus.fa to produce the correct full virus genome from enterobacteria phage lambda
4. challenge.java: the main java file that contains the functions for Part A, B and C of the challenge
5. correct gene.txt: the correct DNA sequence processed running getResult.py on lambda_virus.fa
6. lambda_scramble.fa: file given in Part B
7. lambda_virus.fa: file given in Part C

Steps and Commands:
1. Run parse.py: python parse.py
2. Compile challenge.java: javac challenge.java
3. Run challenge.java with the parsed file gene sequence.txt: java challenge < "gene sequence.txt"
4. Run getResult.py to get the correct result: python getResult.py
5. Compare the results from step 3 and 4

Results:
Part B: 
After running Part B with "gene sequence.txt", the following wrong line is found:
Wrong DNA: GCTCCGCCGGCGACCGACGAAGCCGACGACACTACCGTGCCGCCTTCCGAAAATCCTGCTACCACATCGCCAGACACCACAACCGACAACGACGAGATTG

Part C:
The DNA sequence generated from challenge.java matches the correct DNA sequence provided and it's 48502 bases long.