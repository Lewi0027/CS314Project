import memberPic from "@static/images/Placeholder.jpg";
import teamPic from "@static/images/PlaceholderTeam.jpg";

import bscheidtPic from "@static/images/bscheidt_team_image.jpg"
import hlewisPic from "@static/images/hlewis_team_image.png"
import wattg5Pic from "@static/images/wyattg5_team_image.jpg"

export const teamData =
    {
        teamName: "Team Name",
        missionStatement: "Mission Statement",
        imagePath: teamPic,
    };


export const memberData = [
    {
        name: "Brendan Scheidt",
        bio: "I am an undergrad at CSU majoring in computer science. I am also a UTA for CS165 data structures and algorithms. Throughout my career, I want to help shape the future of artificial intelligence by leading projects that push the boundaries of what's possible in fields such as education and environmental studies. To start accomplishing these goals, last year, I wrote a website that is an AI chatbot for helping tutor students in their classes and have it deployed on Heroku. Outside of school, I enjoy producing music and camping.",
        homeTown: "Fort Collins, CO",
        imagePath: bscheidtPic
    },
    {
        name: "Wyatt Gensler",
        bio: "Bio Here",
        homeTown: "Castle Rock, CO",
        imagePath: wattg5Pic
    },
    {
        name: "Team Member 3",
        bio: "Bio Here",
        homeTown: "Hometown",
        imagePath: hlewisPic
    },
    {
        name: "Team Member 4",
        bio: "Bio Here",
        homeTown: "Hometown",
        imagePath: memberPic
    },
    {
        name: "Team Member 5",
        bio: "Bio Here",
        homeTown: "Hometown",
        imagePath: memberPic
    },
];