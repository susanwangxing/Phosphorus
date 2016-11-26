# processes lambda_virus.fa to produce the correct full virus genome from enterobacteria phage lambda
import sys

def main():
	FO = open("correct gene.txt", "w")
	row = 0
	with open ("lambda_virus.fa") as inf:
		for line in inf:
			row = row + 1
			if (row > 1):
				FO.write(line[:-1])
	FO.close()
main()