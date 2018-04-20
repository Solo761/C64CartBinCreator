
# C64 Cartridge Creator tool

This is small utility written in Java (1.8) for converting C64 prg files to BIN files for burning to (E)EPROM chips, of up to 64 kB.

## More info

You can choose from three types of cartidges, *Magic Desk*, *Hucky* and *Inverted Hucky*. You can also choose between two types of C64 loaders for these files. First one is loader from Frank Buß' prg2crt python script that can be found [here](http://www.frank-buss.de/c64/prg2crt/index.html), used here with his permission, and the other is loader from C64 tool 152blks.prg that can be used on real C64 (or emulator like Vice) to convert prg files from PRG to *Hucky* type bin files. Now it can be used to also make *Magic Desk* and *Inverted Hucky* type bin files.

From what I've tested both loaders work fine, main difference is that prg2crt loader takes less space, but it doesn't "hide" loading and processing from the screen. 152blks loader uses more space for itself, but it blacks the screen so while it loads from the cartridge you don't see anything happening. So to each to his own.

Beside bin files it can also create CRT for use (or testing) with emulators. With exception of *Hucky* cartridges which aren't supported in CRT file format (at least yet). If you choose combination of Hucky and CRT files tool will warn you about this and ask if you want to abort or continue to create *Inverted Hucky* (also called *RGCD*) CRT instead. 
Basically, difference between *Hucky* and *Inverted Hucky* is order of memory banks, everything else is the same.

## GUI Usage

Basically just start jar file. Either from command line

`java -jar cartCreator.jar`

or just double click jar file from windows, on my PC (Win 10) it automatically starts the tool.

Tool should also work on other OS' that have Java, I tried to avoid OS specific stuff in the code.

## Command Line usage

Tool also supports command line usage, enter 

`java -jar cartCreator.jar -h`

in command line to see help


- Parameters:
	-  **-i \<input file\>** -prg file to convert`
	-  **-t \<cartidge type\>**	- which cartridge type to convert to
		- Cartridge types:
			- **h** - Hucky 64kB
			- **ih** - inverted Hucky 64kB
			- **md** - MagicDesk 64kB
	- **-l \<loader type\>** - which loader to use
		 - Loader types:
			- **a** - prg2crt loader from Frank Buß' python script
			- **b** - 152Blks loader

- Semioptional (you need at least one of them):
	- **-c** - convert to emulation cartridge format (CRT)
	- **-b** - convert to bin file for burning to (E)EPROM

- Optional:
	 - **-o \<output file\>** - output file to save converted file to, if it differs from input file
	- **-h** - 				help`

Order of parameters is not important.

For example:

Convert prg to inverted hucky bin file using "a" variant loader

`java -jar cartConv.jar -i BubbleBobble.prg -t ih -b -l a`

Convert prg to MagicDesk CRT file for use in emulator (e.g. Vice) and bin file using "b" variant loader

`java -jar cartConv.jar -i BubbleBobble.prg -o BubbleBobbleEmu.crt -t md -l b -c -b`