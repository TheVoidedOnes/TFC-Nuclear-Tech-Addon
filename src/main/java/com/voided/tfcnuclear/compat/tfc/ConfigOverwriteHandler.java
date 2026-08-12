package com.voided.tfcnuclear.compat.tfc;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ConfigOverwriteHandler {

    private static final String[] HBM_KEYS = {
            "2.L00_enableHematite",
            "2.L01_enableMalachite",
            "2.L02_enableBauxite"
    };

    private static final String TFC_KEY = "forceDefaultOreGenFile";
    private static boolean hasRun = false;

    public static void applyConfigOverwrites() {
        if (!Loader.isModLoaded("tfc")) {
            return;
        }

        try {
            modifyBedrockOresJson();
            modifyDimensionsConfig();
            modifyHbmConfig();
            modifyTFCConfig();
        } catch (Exception ignored) {
        }
    }

    private static void modifyBedrockOresJson() {
        try {
            Path configPath = Paths.get("config/hbm/hbm_bedrock_ores.json");
            if (!Files.exists(configPath)) {
                return;
            }

            String content = new String(Files.readAllBytes(configPath), StandardCharsets.UTF_8);
            String newContent = content;

            if (content.contains("\"dimID\": 0,")) {
                int dimIndex = content.indexOf("\"dimID\": 0,");
                int oreRarityIndex = content.indexOf("\"oreRarity\"", dimIndex);
                if (oreRarityIndex != -1) {
                    int colonIndex = content.indexOf(":", oreRarityIndex);
                    int commaIndex = content.indexOf(",", colonIndex);
                    if (commaIndex != -1) {
                        String oldValue = content.substring(colonIndex + 1, commaIndex).trim();
                        if (oldValue.equals("15")) {
                            newContent = content.substring(0, colonIndex + 1) + " 60," + content.substring(commaIndex + 1);
                        }
                    }
                }
            }

            if (!newContent.equals(content)) {
                Files.write(configPath, newContent.getBytes(StandardCharsets.UTF_8));
            }

        } catch (Exception ignored) {
        }
    }

    private static void modifyDimensionsConfig() {
        try {
            Path configPath = Paths.get("config/hbm/hbm_dimensions.cfg");
            if (!Files.exists(configPath)) {
                return;
            }

            List<String> lines = Files.readAllLines(configPath, StandardCharsets.UTF_8);
            boolean modified = false;
            List<String> newLines = new ArrayList<>();
            boolean inBedrockOilBlock = false;
            int bedrockOilBracketDepth = 0;

            for (String line : lines) {
                String trimmedLine = line.trim();
                String newLine = line;

                if (trimmedLine.startsWith("S:01.31_bedrockOilSpawnRate")) {
                    inBedrockOilBlock = true;
                    bedrockOilBracketDepth = 0;
                }

                if (inBedrockOilBlock) {
                    bedrockOilBracketDepth += line.chars().filter(ch -> ch == '<').count();
                    bedrockOilBracketDepth -= line.chars().filter(ch -> ch == '>').count();
                }

                if (inBedrockOilBlock && bedrockOilBracketDepth > 0) {
                    if (trimmedLine.matches("^0:200$")) {
                        newLine = line.replace("0:200", "0:5000");
                        modified = true;
                    }
                    if (trimmedLine.matches("^-6:200$")) {
                        newLine = line.replace("-6:200", "-6:5000");
                        modified = true;
                    }
                }

                if (inBedrockOilBlock && bedrockOilBracketDepth == 0 && trimmedLine.contains(">")) {
                    inBedrockOilBlock = false;
                }

                newLines.add(newLine);
            }

            if (modified) {
                Files.write(configPath, newLines, StandardCharsets.UTF_8);
            }

        } catch (Exception ignored) {
        }
    }

    private static void modifyHbmConfig() {
        try {
            Path configPath = Paths.get("config/hbm/hbm.cfg");
            if (!Files.exists(configPath)) {
                return;
            }

            List<String> lines = Files.readAllLines(configPath, StandardCharsets.UTF_8);
            boolean modified = false;
            List<String> newLines = new ArrayList<>();

            for (String line : lines) {
                String newLine = line;
                for (String key : HBM_KEYS) {
                    if (line.contains(key) && line.contains("=true")) {
                        newLine = line.replace("=true", "=false");
                        if (!line.equals(newLine)) {
                            modified = true;
                        }
                        break;
                    }
                }
                newLines.add(newLine);
            }

            if (modified) {
                Files.write(configPath, newLines, StandardCharsets.UTF_8);
            }

        } catch (Exception ignored) {
        }
    }

    private static void modifyTFCConfig() {
        try {
            Path configPath = Paths.get("config/TerraFirmaCraft - General.cfg");
            if (!Files.exists(configPath)) {
                return;
            }

            List<String> lines = Files.readAllLines(configPath, StandardCharsets.UTF_8);
            boolean modified = false;
            List<String> newLines = new ArrayList<>();

            for (String line : lines) {
                String newLine = line;

                if (line.contains(TFC_KEY) && line.contains("=true")) {
                    newLine = line.replace("=true", "=false");
                    if (!line.equals(newLine)) {
                        modified = true;
                    }
                }

                newLines.add(newLine);
            }

            if (modified) {
                Files.write(configPath, newLines, StandardCharsets.UTF_8);
            }

        } catch (Exception ignored) {
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        if (!hasRun) {
            hasRun = true;
            applyConfigOverwrites();
        }
    }
}