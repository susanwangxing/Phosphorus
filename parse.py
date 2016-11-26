# parses the lambda_scramble.fa file to obtain the gene sequences
import sys

def main():
	FO = open("gene sequence.txt", "w")
	with open ("lambda_scramble.fa") as inf:
		row = 0
		for line in inf:
			row = row + 1
			if (row % 2 != 1):
				FO.write(line)
	FO.close()
main()