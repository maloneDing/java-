/**
 * 
 */
package com.ahstu.cels.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.ahstu.cels.dao.IBaseTermDao;
import com.ahstu.cels.dao.impl.BaseTermDaoImpl;
import com.ahstu.cels.entity.PageBean;
import com.ahstu.cels.entity.Vocabular;
import com.ahstu.cels.entity.Word;
import com.ahstu.cels.service.IBaseTermService;

/**
 * @description 此类的描述
 * @author ahstu 丁云龙
 * @createDate 上午8:53:12
 * @version ver1.0
 */
public class BaseTermServiceImpl implements IBaseTermService {

	// 以dao为支撑
	private IBaseTermDao baseTermDao = new BaseTermDaoImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ahstu.cels.service.IBaseTermService#getAllWords()
	 */
	@Override
	public Map<Character, List<Word>> getAllWords() {

		// 1.创建一个map，用来保存最终的结果
		Map<Character, List<Word>> results = new TreeMap<>();
		// 2.遍历由dao获取的Set<Word>集合
		Set<Word> words = baseTermDao.getAllWords();
		// 定义一个List集合来存放相同首字母的单词的集合
		List<Word> wordList = null;// 暂不初始化
		// 3.一次判断这个Word的首字母是否出现在map中，如果没有出现在map中，则创建一个新的List,用来存放这个word，并把
		// 首字母与这个List添加到map中。
		for (Word w : words) {
			// 获取这个单词的首字母
			Character letter = w.getCaptical();
			// 判断map中是否由这个首字母对应的记录
			if (results.containsKey(letter)) {
				// 说明map中已经有 了首字母记录，直接根据key 来取出value
				wordList = results.get(letter);
			} else {
				// 说明，这个map中没有首字母记录，则创建新的List
				wordList = new ArrayList<>();
				// 并把这个首字母与集合的映射关系添加到map中
				results.put(letter, wordList);
			}
			//把这个单词添加到List中
			wordList.add(w);
		}
		// 返回
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ahstu.cels.service.IBaseTermService#getAllVocabular()
	 */
	@Override
	public PageBean<Vocabular> getAllVocabular() {
		//1. 获取所有词汇
		Set<Vocabular> vSet = baseTermDao.getAllvocabular();
		//2. 创建pageBean对象
		final int ROWS = 15;//每页显示15行
		PageBean<Vocabular> pb= new PageBean<>(vSet.size(), ROWS);
		//添加数据到此PageBean中
		List<Vocabular> data = new ArrayList<>();
		//把Set集合中的数据添加到List 集合中
		for(Vocabular v:vSet){
			data.add(v);
		}
		//
		pb.setData(data);
		//3. 返回
		return pb;
	}

}
