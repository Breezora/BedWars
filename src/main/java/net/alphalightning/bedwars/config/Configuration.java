package net.alphalightning.bedwars.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.eldoria.eldoutilities.config.ConfigKey;
import de.eldoria.eldoutilities.config.JacksonConfig;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class Configuration extends JacksonConfig<Default> {

    private final Skin team = new Skin()
            .signature("V80u2DulBAvy7A+bf50aLbwVFb3CUQygbVcfz3ZGdkuAzJUVlJzqbfOP626S+bVoAWjU1aybabKEl944KpjF5/p5OV+ruepb6y1Mos1R8W4dw6q+NO4q1K4myTDAyZUHE++1FMwKUgNGNoOaEqFtzHmrQ0XgJnKNiqTDhwLDEkYsPHd00j23UjRgdKiSNNyiWrOc7WDAl8+lpxDOVxrb5+IhH8TqPEcPTDXefUZScr3k6TIBF2eZ6FNYIPeZIKBzAEAdt0VNMzuEh/n87djy4" +
                    "++/7FxnLo4vebnnzkVegWWCygTLW+huAlXjCe9S2ItAqs9vFNOf861xtYg+1tLidZYA3wbsd7NsyoT7pjgU7qqSLmZCuPwermRx67RPtUjHQOJwLX3kJhy5ZQGb8q6WH+v5cOp/DNERf5FhbNDvLkrTDRNtEDUaPsgdD75CUP2qQLbnI0l30IEfp/jOlzP1AVluk6Rn61pRPlm0Nva2oBoqzV0Hw3+yNCBSxcy1HGTvEUlA/Fqol114mQRjmtJmZ8C7P9Dhfnpr9U0MNbLTCkBAdlNol6ehw+EU8G0K0wskPa6bH4CI50tYdR6Zhq3viV4OcX9RzNO1Kpv1Zg94ImF9Eml83bIHo8AZ1xA0j5Wj0mA9KRbUfrQR1sAq75fq7HJABXKeFaTZv4yd7BJ5zds=")
            .value(
                    "ewogICJ0aW1lc3RhbXAiIDogMTYxNDU1NDk5MDAwNiwKICAicHJvZmlsZUlkIiA6ICI2MTY5OWIyZWQzMjc0YTAxOWYxZTBlYThjM2YwNmJjNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJEaW5uZXJib25lIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzUwYzQxMGZhZDhkOWQ4ODI1YWQ1NmIwZTQ0M2UyNzc3YTZiNDZiZmEyMGRhY2QxZDJmNTVlZGM3MWZiZWIwNmQiCiAgICB9LAogICAgIkNBUEUiIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzU3ODZmZTk5YmUzNzdkZmI2ODU4ODU5ZjkyNmM0ZGJjOTk1NzUxZTkxY2VlMzczNDY4YzVmYmY0ODY1ZTcxNTEiCiAgICB9CiAgfQp9=");
    private final Skin spectator = new Skin()
            .signature("ewogICJ0aW1lc3RhbXAiIDogMTYyMDgyMzMzMDQ5MSwKICAicHJvZmlsZUlkIiA6ICJmZDYwZjM2ZjU4NjE0ZjEyYjNjZDQ3YzJkODU1Mjk5YSIsCiAgInByb2ZpbGVOYW1lIiA6ICJSZWFkIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzJmZjZkOTcwYjFiNjI0M2ZlNWE0NGM1YWM1NDBjMzIwNTA2OTg3YTVjNTViYTk5YTkwZjc1OGIwMGQzZTA1YTEiCiAgICB9CiAgfQp9")
            .value("hYXq2rTaVqTdQ0DfgBj6qQLlcc1UOreIeYO5ChO/OZkDaBWE5VosM9+1Y8AOq74+iAv+xASGa5YXLVJ0eqd6ISUaiogiJ4mtSQLbYycI/v9I/DULMGuY1riPTBvUARUYFJMYjOvZQnURlzXot/6og6X0Tr0xSxeoxQB0j1JhXIM8pjjOWH9x9aCWtB2PMyMIN07hFEUNJbIqXc6/ITB0LW/tJWNedIdVQljXzUvJ8r89QvXvcN07S7jflYy3SVEFfh9iUWqRyAt8aMl3Y5ZdRDY04Le6Wi9qJORDTAUL+siCW+RO8XzEoywsodbDHIzIIKuG2OmqfZyf6KU1WCPgfBnU3gYUrKdAIg8AdVGDZbSaUxVkjR3bwwP67dqcOK3XXb7pACgBYGLrgPi8cu6Bp1+plRaA81xmXIrrbHxmqqfFk77dihJsOzQXTTM8dnPEZhOrnPkpMsugk3do+qd8gOtRaqbwuLfIVFrYTHR6C09vSLMwgUIt4CMiEvGwf8UHS7xJpqqEcSfdvbfauXpi9t0e6dCGNmkYOQ+W6TCWTZPEwppoZDIoMZpMIUnBvjZ2lFkkBefIqWakKXhr76PV7cx3Ra+w+7V4Hf7bpxPCcGDUIumBGlRq2T1AreSvrs4eQqlVEBaVAjfK8S2o0gfWkQsozT8WF2064PhklcXp9Gs=");

    private static final ConfigKey<Default> MAIN_KEY = ConfigKey.of("config", Path.of("config.json"), Default.class, Default::new);
    private static final ConfigKey<Skins> SKINS_KEY = ConfigKey.of("skins", Path.of("skins.json"), Skins.class, Skins::new);

    private final ObjectMapper mapper;

    public Configuration(@NotNull Plugin plugin, ObjectMapper mapper) {
        super(plugin, MAIN_KEY);
        this.mapper = mapper;
    }

    public Skins skins() {
        return secondary(SKINS_KEY);
    }

    @Override
    protected ObjectMapper createMapper() {
        return mapper;
    }

    public void createOrDoNothing() {
        if (exists(MAIN_KEY)) {
            return;
        }

        main().environment(Environment.DEVELOPMENT);
        skins().team(team);
        skins().spectator(spectator);
        save();
    }
}
