package com.plantcare.knowledgebase.config;

import com.plantcare.knowledgebase.entity.CareGuide;
import com.plantcare.knowledgebase.repository.CareGuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CareGuideRepository careGuideRepository;

    @Override
    public void run(String... args) throws Exception {
        if (careGuideRepository.count() == 0) {
            List<CareGuide> entries = new ArrayList<>();

            // 1. Care Guides
            entries.add(createEntry("Monstera Deliciosa", "Care Guides", "Complete Monstera Care Guide", "Provide bright, indirect light and water when the top inch of soil is dry. Monstera deliciosa loves humidity and needs a moss pole for support as it grows. Wipe the large leaves with a damp cloth to keep them dust-free and healthy. Fertilize monthly during the growing season with a balanced liquid fertilizer."));
            entries.add(createEntry("Snake Plant", "Care Guides", "The Ultimate Snake Plant Guide", "Sansevieria is one of the hardiest plants. It thrives in low to bright light and requires very little water. Allow the soil to dry completely between waterings, especially in winter. Overwatering is the most common cause of failure. It's also an excellent air purifier for bedrooms."));
            entries.add(createEntry("Fiddle Leaf Fig", "Care Guides", "Mastering the Fiddle Leaf Fig", "Ficus lyrata needs consistent bright, indirect light. Water only when the top 2 inches of soil feel dry. They are sensitive to environment changes, so avoid moving them once they're happy. Use a well-draining potting mix and ensure the pot has drainage holes. Clean the leaves regularly to allow for efficient photosynthesis."));
            entries.add(createEntry("Pothos", "Care Guides", "Beginner's Guide to Pothos", "Epipremnum aureum is perfect for beginners. It adapts to various lighting conditions, from low to bright light. Water when the soil is mostly dry. Its trailing vines make it ideal for hanging baskets or shelves. Easy to propagate in water or soil."));
            entries.add(createEntry("ZZ Plant", "Care Guides", "ZZ Plant: The Indestructible Choice", "Zamioculcas zamiifolia is extremely drought-tolerant. It thrives in low light environments where other plants might struggle. Water once every 3-4 weeks. The waxy leaves store water, making it a low-maintenance favorite for offices and busy owners."));
            entries.add(createEntry("Spider Plant", "Care Guides", "Caring for Your Spider Plant", "Chlorophytum comosum prefers bright, indirect light but can tolerate lower light. Water regularly but don't let it sit in soggy soil. It produces 'babies' on long stems which can be easily rooted in water to create new plants. Excellent for improving indoor air quality."));
            entries.add(createEntry("Peace Lily", "Care Guides", "Peace Lily Care and Maintenance", "Spathiphyllum loves high humidity and consistent moisture. It will 'tell' you when it's thirsty by drooping significantly. It prefers medium to low light. Be aware that it is toxic to pets if ingested. It produces beautiful white spathes throughout the year."));
            entries.add(createEntry("Aloe Vera", "Care Guides", "Aloe Vera: Practical Care Tips", "This succulent needs bright light, ideally 6 hours of direct sun daily. Use a sandy, well-draining cactus mix. Water deeply but infrequently, allowing the soil to dry completely. The gel inside the leaves is famous for its soothing properties on burns and skin irritations."));
            entries.add(createEntry("Bird of Paradise", "Care Guides", "Stunning Bird of Paradise Care", "Strelitzia reginae needs lots of light to bloom, including some direct sun. It enjoys rich soil and regular watering during summer, but let it dry out more in winter. Its large, tropical leaves create a dramatic focal point in any room with high ceilings."));
            entries.add(createEntry("Rubber Tree", "Care Guides", "Rubber Tree (Ficus elastica) Guide", "Ficus elastica prefers bright, indirect light and well-draining soil. Water when the top inch of soil is dry. Its thick, glossy leaves can be dark green, burgundy, or variegated. Be careful with the sap, as it can be irritating to skin and toxic to pets."));

            // 2. Problem Solutions
            entries.add(createEntry("General", "Problem Solutions", "Yellowing Leaves: Causes and Cures", "Yellow leaves are usually a sign of overwatering or poor drainage. Check the roots for rot. Other causes include nutrient deficiency (lack of nitrogen) or natural aging of older leaves. Adjust your watering schedule and ensure your pot has adequate drainage."));
            entries.add(createEntry("General", "Problem Solutions", "Dealing with Spider Mites", "Spider mites are tiny pests that leave fine webbing on plants. Increase humidity and wash the plant with a strong stream of water. Use neem oil or insecticidal soap for persistent infestations. Keep newly affected plants isolated from others."));
            entries.add(createEntry("General", "Problem Solutions", "Brown Leaf Tips: Troubleshooting", "Brown tips often indicate low humidity, underwatering, or salt buildup from tap water. Try misting your plant, using a humidifier, or switching to filtered/distilled water. Ensure you are watering deeply enough so that water reaches all the roots."));
            entries.add(createEntry("General", "Problem Solutions", "Root Rot Identification and Treatment", "Symptoms include wilting despite wet soil and a foul smell from the pot. Remove the plant from its pot, cut away mushy brown roots, and repot in fresh, dry soil. Use a sterilized container and improve your soil's drainage with perlite or pumice."));
            entries.add(createEntry("General", "Problem Solutions", "How to Eradicate Fungus Gnats", "Fungus gnats thrive in moist topsoil. Allow the top 2 inches of soil to dry out between waterings. Use yellow sticky traps to catch adults and drench the soil with a diluted hydrogen peroxide solution (1 part 3% peroxide to 4 parts water) to kill larvae."));
            entries.add(createEntry("General", "Problem Solutions", "Mealybug Infestation Guide", "Mealybugs look like small tufts of white cotton on stems and leaves. Remove them manually using a cotton swab dipped in rubbing alcohol. For larger outbreaks, use neem oil or an organic insecticidal soap consistently every few days until they are gone."));
            entries.add(createEntry("General", "Problem Solutions", "Scale Insects: Prevention and Cure", "Scale appear as small brown bumps on stems. They suck the sap and weaken the plant. Scrape them off manually or treat with horticultural oil. Prune heavily infested branches if necessary to prevent the spread to other plants."));
            entries.add(createEntry("General", "Problem Solutions", "White Mold on Soil Surface", "Saprophytic fungus is usually harmless but indicates the soil is staying too damp. Scrape off the mold, sprinkle cinnamon (a natural fungicide) on the surface, and improve air circulation. Reduce watering frequency and ensure the soil surface dries out."));
            entries.add(createEntry("General", "Problem Solutions", "Leggy Growth: Solving Etiolation", "When plants grow long, thin stems with sparse leaves, they are 'reaching' for more light. Move the plant to a brighter location. Prune back the leggy growth to encourage bushier new stems. Rotate the plant weekly so all sides get equal light."));
            entries.add(createEntry("General", "Problem Solutions", "Nutrient Deficiencies Explained", "Interveinal yellowing often means magnesium or iron deficiency. Purple undersides can indicate phosphorus issues. Stunted growth might be lack of nitrogen. Use a balanced, water-soluble fertilizer to provide a steady supply of essential macro and micronutrients."));

            // 3. Seasonal Tips
            entries.add(createEntry("All Plants", "Seasonal Tips", "Winter Care: The Rest Period", "Most plants go dormant in winter. Reduce watering frequency as the soil dries slower. Stop fertilizing until spring. Keep plants away from cold drafts or heat vents. Since daylight is shorter, move plants closer to windows if possible to maximize light intake."));
            entries.add(createEntry("All Plants", "Seasonal Tips", "Spring Prep: Awakening Your Garden", "Spring is the time to repot and start fertilizing again. Prune away any dead winter growth to make room for new shoots. As the sun gets stronger, monitor your plants for signs of sunburn and move them back from windows if necessary."));
            entries.add(createEntry("Outdoor", "Seasonal Tips", "Summer Heat Protection", "During heatwaves, water your outdoor plants early in the morning or late in the evening to minimize evaporation. Use mulch to keep the soil cool and retain moisture. Provide temporary shade for sensitive plants during the hottest part of the afternoon."));
            entries.add(createEntry("Outdoor", "Seasonal Tips", "Autumn Cleanup and Preparation", "Clear away fallen leaves to prevent fungal diseases. Start bringing sensitive tropicals indoors before the first frost. Plant spring bulbs like tulips and daffodils now. Harvest the last of your summer vegetables and compost the spent plants."));
            entries.add(createEntry("Indoor", "Seasonal Tips", "Adjusting Humidity for Winter", "Indoor heating dries out the air significantly. Use a pebble tray with water, group plants together to create a microclimate, or use a humidifier. Tropical plants especially need this extra moisture to prevent crispy leaf edges during the dry winter months."));
            entries.add(createEntry("Edibles", "Seasonal Tips", "When to Plant Summer Veggies", "Wait until the soil has warmed up and the danger of frost has passed before planting tomatoes, peppers, and cucumbers. Most summer crops need at least 6-8 hours of direct sunlight. Mulch well to maintain consistent soil moisture during the growing season."));
            entries.add(createEntry("Flowers", "Seasonal Tips", "Deadheading for Continuous Blooms", "Regularly removing spent flowers (deadheading) prevents the plant from putting energy into seed production. This encourages the plant to produce more blooms throughout the season. Especially effective for roses, petunias, and marigolds."));
            entries.add(createEntry("All Plants", "Seasonal Tips", "Managing Pests in Spring", "As the weather warms up, pests like aphids and gnats become more active. Inspect the new growth on your plants weekly. Early detection is key to preventing a small issue from becoming a full-blown infestation. Catch them early and treat naturally."));
            entries.add(createEntry("Succulents", "Seasonal Tips", "Succulent Winter Care", "Succulents are particularly sensitive to overwatering in winter. Water only when the leaves look slightly wrinkled. Keep them in the brightest spot available. Cool night temperatures (above freezing) can sometimes trigger beautiful stress colors in certain species."));
            entries.add(createEntry("Outdoor", "Seasonal Tips", "Frost Protection Methods", "Use frost blankets or burlap to cover sensitive shrubs during unexpected late frosts. Water the soil before a freeze, as moist soil holds more heat than dry soil. Move potted outdoor plants into a garage or shed temporarily for protection."));

            // 4. Plant Database
            entries.add(createEntry("Calathea Orbifolia", "Plant Database", "Calathea Orbifolia (Prayer Plant)", "Distinguished by its large, round, silver-striped leaves. It's a member of the Marantaceae family, known for moving its leaves up at night. Prefers high humidity and distilled water. Native to the tropical forests of Bolivia and Brazil."));
            entries.add(createEntry("Swiss Cheese Plant", "Plant Database", "Monstera Adansonii", "Famous for its smaller leaves with natural holes (fenestrations). It's a vigorous climber that looks great on a trellis or trailing from a shelf. Native to Central and South America. Often confused with Monstera obliqua, which is much rarer."));
            entries.add(createEntry("String of Pearls", "Plant Database", "Curio rowleyanus", "A unique succulent with pea-shaped leaves on trailing stems. It grows naturally in the shade of rocks in South Africa. Needs bright, indirect light and minimal water. Be careful not to get water on the pearls, as it can cause them to rot."));
            entries.add(createEntry("African Violet", "Plant Database", "Saintpaulia ionantha", "Features fuzzy leaves and clusters of purple, pink, or white flowers. They thrive in small pots and prefer bottom-watering to avoid getting the leaves wet, which can cause spotting. Native to Tanzania and Kenya. Excellent for indoor windowsill gardening."));
            entries.add(createEntry("Polka Dot Plant", "Plant Database", "Hypoestes phyllostachya", "Small plant with colorful spotted foliage in shades of pink, red, or white. It needs bright light to keep its vibrant colors. If it gets leggy, don't be afraid to pinch it back to encourage bushier growth. Native to Madagascar."));
            entries.add(createEntry("Nerve Plant", "Plant Database", "Fittonia albivenis", "Small tropical plant with striking veined leaves (white, pink, or red). It is a 'drama queen' that wilts completely when dry but bounces back quickly once watered. Ideal for terrariums because it loves high humidity and low light."));
            entries.add(createEntry("Air Plant", "Plant Database", "Tillandsia species", "Epiphytes that grow without soil. They absorb moisture and nutrients through their leaves. Soak them in water once a week for 20-30 minutes and ensure they dry completely within 4 hours. There are hundreds of species with various shapes and sizes."));
            entries.add(createEntry("Majesty Palm", "Plant Database", "Ravenea rivularis", "A beautiful, feathery palm that can reach 10 feet indoors. It requires high light and high humidity. It's native to Madagascar where it grows along riverbanks, meaning it needs more water than many other palms. Often used to add a tropical feel."));
            entries.add(createEntry("String of Turtles", "Plant Database", "Peperomia prostrata", "A tiny, trailing succulent with leaves that resemble turtle shells. It's a slow grower but becomes a thick mat over time. Prefers bright, indirect light and shallow pots. Great for small spaces or as an accent in larger plant displays."));
            entries.add(createEntry("Rex Begonia", "Plant Database", "Begonia rex-cultorum", "Prized for its extraordinary foliage in metallic silvers, deep purples, and vibrant reds. It prefers cool temperatures and high humidity. Avoid direct sun, which can scorch the delicate leaves. A true work of art in the botanical world."));

            // 5. User Tips
            entries.add(createEntry("Home Remedy", "User Tips", "The Cinnamon Trick", "Sprinkle ground cinnamon on top of the soil for new seedlings or when repotting. It's a natural anti-fungal that helps prevent 'damping off' and keeps the soil healthy without harsh chemicals. It also smells wonderful when you water your plants!"));
            entries.add(createEntry("Watering", "User Tips", "Using an Unglazed Clay Pot", "Terra cotta is porous and allows the soil to breathe. If you tend to overwater your plants, switching to unglazed clay pots can save your plants' lives by allowing excess moisture to evaporate through the walls of the pot."));
            entries.add(createEntry("Propagation", "User Tips", "Honey as Rooting Hormone", "If you don't have rooting powder, use a tiny bit of raw honey on your cuttings before putting them in soil. Honey has natural antibacterial properties that protect the cut and can help stimulate root growth for many common houseplants."));
            entries.add(createEntry("Pests", "User Tips", "The Yellow Sponge Gnat Trap", "If you have fungus gnats, place a small piece of yellow sponge soaked in soapy water near your plants. The yellow color attracts them, and the soapy water traps them. Much cheaper than commercial sticky traps and works surprisingly well."));
            entries.add(createEntry("Lighting", "User Tips", "Rotating for Even Growth", "Give your plants a quarter turn every time you water them. This ensures all sides of the plant get equal exposure to the light source, preventing one-sided, leaning growth and ensuring a full, beautiful shape as the plant matures."));
            entries.add(createEntry("Cleaning", "User Tips", "Banana Peel Leaf Shine", "Use the inside of a banana peel to gently wipe the leaves of your large-leaved plants like Monsteras or Rubber Trees. It removes dust and leaves a beautiful, natural shine without clogging the stomata like commercial leaf shine products often do."));
            entries.add(createEntry("Watering", "User Tips", "The Bottom-Watering Method", "For plants like Peperomias or Violets, place the pot in a tray of water for 15-20 minutes until the top of the soil feels moist. This encourages deep root growth and prevents water from sitting on the crown of the plant, which can lead to rot."));
            entries.add(createEntry("Pests", "User Tips", "Neem Oil Spray Recipe", "Mix 1 teaspoon of pure neem oil and 1/2 teaspoon of mild dish soap into a quart of warm water. Shake well and spray on your plants (including undersides of leaves) to prevent and treat various pests. Reapply every 7 days for best results."));
            entries.add(createEntry("Fertilizing", "User Tips", "Rice Water Fertilizer", "Don't throw away the water after you wash your rice! It's rich in starches and mild nutrients that beneficial soil bacteria love. Let it cool and use it to water your plants for a gentle, organic boost. It's free and completely natural."));
            entries.add(createEntry("Recycling", "User Tips", "Eggshell Calcium Boost", "Crush clean, dry eggshells into a fine powder and mix them into your potting soil or sprinkle around the base of your plants. They provide a slow-release source of calcium which is essential for cell wall strength, especially for tomatoes and peppers."));

            careGuideRepository.saveAll(entries);
        }
    }

    private CareGuide createEntry(String plantType, String category, String title, String content) {
        return CareGuide.builder()
                .plantType(plantType)
                .category(category)
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
