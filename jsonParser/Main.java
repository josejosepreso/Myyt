import java.util.*;
import java.util.stream.*;
import java.io.*;

class JSON<T> {
		private Map<String, T> values;

		public JSON() {
				this.values = new HashMap<>();
		}

		public T add(String key, T value) {
				return this.values.put(key, value);
		}
}

class Pair {
		private Token key;
		private List<Token> value;

		public Pair(Token key, List<Token> value) {
				this.key = key;
				this.value = value;
		}

		public String toString() {
				return String.format("%s %s", this.key, this.value);
		}
}

class JSONParser {
		public static JSON parseFile(String filePath) throws Exception {
				Optional<String> source = FileHandler.getContent(Configuration.JSON_FILE);

				if (!source.isPresent()) {
						throw new IOException();
				}

				Lexer lexer = new Lexer(filePath, source.get());

				System.out.println(lexer.getTokens());

				return null;
		}
}

class Main {
		public static void main(String[] args) throws Exception {
				JSONParser.parseFile(Configuration.JSON_FILE);
		}
}

class FileHandler {
		public static Optional<String> getContent(String path) throws IOException {
				try (BufferedReader file = new BufferedReader(new FileReader(path))) {
						return Optional.of(file.lines().collect(Collectors.joining("\n")));
				} catch (Exception e) {
						return Optional.empty();
				}
		}
}

class Configuration {
		public static final String JSON_FILE = "json";
}

class Loc {
		private String filePath;
		private int row;
		private int col;

		public Loc(String filePath, int row, int col) {
				this.filePath = filePath;
				this.row = row;
				this.col = col;
		}

		@Override
		public String toString() {
				return String.format("%s:%d:%d", this.filePath, this.row + 1, this.col + 1);
		}
}

enum Type {
		TOKEN_NAME,
		TOKEN_NUMBER,
		TOKEN_STRING,
		TOKEN_OCURLY,
		TOKEN_CCURLY,
		TOKEN_COMMA,
		TOKEN_COLON,
		TOKEN_CSQUARE,
		TOKEN_OSQUARE
}

class Token<T> {
		private Type type;
		private Loc loc;
		private T value;

		public Token(Type type, Loc loc, T value) {
				this.type = type;
				this.loc = loc;
				this.value = value;
		}

		@Override
		public String toString() {
				// return String.format("%s %s %s", this.loc, this.type, this.value);
				return String.format("%s %s", this.type, this.value);
		}
}

class Lexer {
		private String filePath;
		private String source;
		private int cur;
		private int bol;
		private int row;

		public Lexer(String filePath, String source) {
				this.filePath = filePath;
				this.source = source;
				this.cur = 0;
				this.bol = 0;
				this.row = 0;
		}

		public boolean isNotEmpty() {
				return this.cur < this.source.length();
		}

		public boolean isEmpty() {
				return !this.isNotEmpty();
		}

		public void nextChar() {
				if (this.isEmpty()) {
						return;
				}

				char c = this.source.charAt(this.cur);
				this.cur++;

				if (c == '\n') {
						this.bol = this.cur;
						this.row++;
				}
		}

		public Loc loc() {
				return new Loc(this.filePath, this.row, this.cur - this.bol);
		}

		public void trimLeft() {
				while (this.isNotEmpty() && Character.isWhitespace(this.source.charAt(this.cur))) {
						this.nextChar();
				}
		}

		public void dropLine() {
				while (this.isNotEmpty() && this.source.charAt(this.cur) != '\n') {
						this.nextChar();
				}

				if (this.isNotEmpty()) {
						this.nextChar();
				}
		}

		public Optional<Token> nextToken() {
				this.trimLeft();
				while (this.isNotEmpty()) {
						String substring = this.source.substring(this.cur);

						if (substring.substring(1) != "//") {
								break;
						}

						this.dropLine();
						this.trimLeft();
				}

				if (this.isEmpty()) {
						return Optional.empty();
				}

				Loc loc = this.loc();
				char first = this.source.charAt(this.cur);

				Type type = switch (first) {
				case '{' -> Type.TOKEN_OCURLY;
				case '}' -> Type.TOKEN_CCURLY;
				case ',' -> Type.TOKEN_COMMA;
				case ':' -> Type.TOKEN_COLON;
				case '[' -> Type.TOKEN_OSQUARE;
				case ']' -> Type.TOKEN_CSQUARE;
				default -> null;
				};

				if (type != null) {
						this.nextChar();
						return Optional.of(new Token<>(type, loc, first));
				}

				if (Character.isLetter(first)) {
						int index = this.cur;

						while (this.isNotEmpty() && Character.isLetterOrDigit(this.source.charAt(this.cur))) {
								this.nextChar();
						}

						String value = this.source.substring(index, this.cur);
						return Optional.of(new Token<>(Type.TOKEN_NAME, loc, value));
				}

				if (first == '"') {
						this.nextChar();
						int start = this.cur;
						String literal = "";
						boolean exit = false;

						while (this.isNotEmpty() && !exit) {
								char c = this.source.charAt(this.cur);

								switch (c) {
								case '"':
										exit = true;
										break;
								default:
										literal += c;
										this.nextChar();
								}
						}

						if (this.isNotEmpty()) {
								this.nextChar();
								return Optional.of(new Token<>(Type.TOKEN_STRING, loc, literal));
						}

						return Optional.empty();
				}

				if (Character.isDigit(first)) {
						int start = this.cur;
						while (this.isNotEmpty() && Character.isDigit(this.source.charAt(this.cur))) {
								this.nextChar();
						}

						String value = this.source.substring(start, this.cur);
						return Optional.of(new Token<>(Type.TOKEN_NUMBER, loc, value));
				}

				this.nextChar();
				return Optional.empty();
		}

		public List<Token> getTokens() throws Exception {
				if (this.isEmpty()) {
						return Collections.emptyList();
				}

				List<Token> tokens = new ArrayList<>();

				while (this.isNotEmpty()) {
						Optional<Token> token = this.nextToken();

						if (!token.isPresent()) {
								throw new Exception();
						}

						tokens.add(token.get());
				}

				return tokens;
		}
}
