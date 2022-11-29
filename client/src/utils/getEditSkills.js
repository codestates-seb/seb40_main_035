//수정페이지 기술스택선택란에 이전 정보 받아오기 위한 형식 변경
import {
  SiJavascript,
  SiTypescript,
  SiReact,
  SiVuedotjs,
  SiSvelte,
  SiNextdotjs,
  SiJava,
  SiSpring,
  SiNodedotjs,
  SiGo,
  SiKotlin,
  SiExpress,
  SiMysql,
  SiMongodb,
  SiPython,
  SiDjango,
  SiPhp,
  SiGraphql,
  SiFirebase,
  SiFlutter,
  SiSwift,
  SiUnity,
  SiAmazonaws,
  SiKubernetes,
  SiDocker,
  SiGit,
  SiFigma,
  SiJest,
  SiCplusplus,
} from 'react-icons/si';
import { GiZeppelin } from 'react-icons/gi';

const getEditSkills = (res) => {
  let arrSkills = [];

  for (let el of res) {
    if (el.skillSort === '프론트엔드') {
      if (el.name === 'JavaScript') {
        arrSkills.push({
          name: 'JavaScript',
          img: <SiJavascript className="interest-tag-img" color="#F7DF1E" />,
        });
      }

      if (el.name === 'TypeScript') {
        arrSkills.push({
          name: 'TypeScript',
          img: <SiTypescript className="interest-tag-img" color="#3178C6" />,
        });
      }
      if (el.name === 'React') {
        arrSkills.push({
          name: 'React',
          img: <SiReact className="interest-tag-img" color="#61DAFB" />,
        });
      }

      if (el.name === 'Vue') {
        arrSkills.push({
          name: 'Vue',
          img: <SiVuedotjs className="interest-tag-img" color="#4FC08D" />,
        });
      }

      if (el.name === 'Svelte') {
        arrSkills.push({
          name: 'Svelte',
          img: <SiSvelte className="interest-tag-img" color="#FF3E00" />,
        });
      }

      if (el.name === 'Nextjs') {
        arrSkills.push({
          name: 'Nextjs',
          img: <SiNextdotjs className="interest-tag-img" color="#000000" />,
        });
      }
    }

    if (el.skillSort === '백엔드') {
      if (el.name === 'Java') {
        arrSkills.push({
          name: 'Java',
          img: <SiJava className="interest-tag-img" color="#000000" />,
        });
      }

      if (el.name === 'Spring') {
        arrSkills.push({
          name: 'Spring',
          img: <SiSpring className="interest-tag-img" color="#6DB33F" />,
        });
      }
      if (el.name === 'Nodejs') {
        arrSkills.push({
          name: 'Nodejs',
          img: <SiNodedotjs className="interest-tag-img" color="#339933" />,
        });
      }

      if (el.name === 'Nextjs') {
        arrSkills.push({
          name: 'Nextjs',
          img: <SiNextdotjs className="interest-tag-img" color="#000000" />,
        });
      }

      if (el.name === 'Go') {
        arrSkills.push({
          name: 'Go',
          img: <SiGo className="interest-tag-img" color="#00ADD8" />,
        });
      }

      if (el.name === 'Kotlin') {
        arrSkills.push({
          name: 'Kotlin',
          img: <SiKotlin className="interest-tag-img" color="#FE8901" />,
        });
      }

      if (el.name === 'Express') {
        arrSkills.push({
          name: 'Express',
          img: <SiExpress className="interest-tag-img" color="#000000" />,
        });
      }

      if (el.name === 'MySQL') {
        arrSkills.push({
          name: 'MySQL',
          img: <SiMysql className="interest-tag-img" color="#4479A1" />,
        });
      }
      if (el.name === 'MongoDB') {
        arrSkills.push({
          name: 'MongoDB',
          img: <SiMongodb className="interest-tag-img" color="#47A248" />,
        });
      }

      if (el.name === 'Python') {
        arrSkills.push({
          name: 'Python',
          img: <SiPython className="interest-tag-img" color="#3776AB" />,
        });
      }

      if (el.name === 'Django') {
        arrSkills.push({
          name: 'Django',
          img: <SiDjango className="interest-tag-img" color="#092E20" />,
        });
      }

      if (el.name === 'php') {
        arrSkills.push({
          name: 'php',
          img: <SiPhp className="interest-tag-img" color="#777BB4" />,
        });
      }

      if (el.name === 'GraphQL') {
        arrSkills.push({
          name: 'GraphQL',
          img: <SiGraphql className="interest-tag-img" color="#E10098" />,
        });
      }

      if (el.name === 'Firebase') {
        arrSkills.push({
          name: 'Firebase',
          img: <SiFirebase className="interest-tag-img" color="#FFCA28" />,
        });
      }
    }

    if (el.skillSort === '기타') {
      if (el.name === 'Flutter') {
        arrSkills.push({
          name: 'Flutter',
          img: <SiFlutter className="interest-tag-img" color="#02569B" />,
        });
      }
      if (el.name === 'Swift') {
        arrSkills.push({
          name: 'Swift',
          img: <SiSwift className="interest-tag-img" color="#F05138" />,
        });
      }
      if (el.name === 'ReactNative') {
        arrSkills.push({
          name: 'ReactNative',
          img: <SiReact className="interest-tag-img" color="#61DAFB" />,
        });
      }
      if (el.name === 'Unity') {
        arrSkills.push({
          name: 'Unity',
          img: <SiUnity className="interest-tag-img" color="#000000" />,
        });
      }
      if (el.name === 'AWS') {
        arrSkills.push({
          name: 'AWS',
          img: <SiAmazonaws className="interest-tag-img" color="#232F3E" />,
        });
      }
      if (el.name === 'Kubernetes') {
        arrSkills.push({
          name: 'Kubernetes',
          img: <SiKubernetes className="interest-tag-img" color="#326CE5" />,
        });
      }
      if (el.name === 'Docker') {
        arrSkills.push({
          name: 'Docker',
          img: <SiDocker className="interest-tag-img" color="#2496ED" />,
        });
      }
      if (el.name === 'Git') {
        arrSkills.push({
          name: 'Git',
          img: <SiGit className="interest-tag-img" color="#F05032" />,
        });
      }
      if (el.name === 'Figma') {
        arrSkills.push({
          name: 'Figma',
          img: <SiFigma className="interest-tag-img" color="#5B0BB5" />,
        });
      }
      if (el.name === 'Zeplin') {
        arrSkills.push({
          name: 'Zeplin',
          img: <GiZeppelin className="interest-tag-img" color="#FF9900" />,
        });
      }
      if (el.name === 'Jest') {
        arrSkills.push({
          name: 'Jest',
          img: <SiJest className="interest-tag-img" color="#C21325" />,
        });
      }
      if (el.name === 'C') {
        arrSkills.push({
          name: 'C',
          img: <SiCplusplus className="interest-tag-img" color="#00599C" />,
        });
      }
    }
  }

  return arrSkills;
};

export default getEditSkills;
